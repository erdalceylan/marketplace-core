package com.marketplaces.core.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplaces.core.dto.create.CustomerUserCreateRequestDTO;
import com.marketplaces.core.entity.MerchantStatus;
import com.marketplaces.core.entity.ProductPoolState;
import com.marketplaces.core.manager.CustomerUserManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Normalizer;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(2)
@Slf4j
public class TrendyolFinalCommand implements CommandLineRunner {

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    private final ObjectMapper objectMapper;
    private final JdbcTemplate jdbcTemplate;
    private final CustomerUserManager customerUserManager;
    private final HashMap<Long, Boolean> groupIdFrags = new HashMap<>();

    private String categoryScrollUrl = "https://apigw.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll";
    private String productGroupIdUrl = "https://apigw.trendyol.com/discovery-web-websfxproductgroups-santral/api/v2/product-groups";
    private String productDetailUrl = "https://apigw.trendyol.com/discovery-web-productgw-service/api/productDetail/";


    TrendyolFinalCommand(
            ObjectMapper objectMapper,
            JdbcTemplate jdbcTemplate,
            CustomerUserManager customerUserManager
    ) {
        this.objectMapper = objectMapper;
        this.jdbcTemplate = jdbcTemplate;
        this.customerUserManager = customerUserManager;
        DBHelper.init(jdbcTemplate);
    }


    @Override
    public void run(String... args) throws Exception {

        Set<String> argList = Set.of(args);
        if (argList.isEmpty() || !argList.contains("Fill-Dummy-Data-Trendyol")) {
            return;
        }

        insertCustomerUsers(5000);
        Set<String> urlList = extractCategoryUrls();

        for (String categoryUrl : urlList) {
            log.info("Starting Category " + categoryUrl);
            for (int page = 0; page < 1; page++) {
                try {
                    String content = Utils.get(categoryScrollUrl + categoryUrl+ "?pi=" + page);
                    JsonNode searchPage = objectMapper.readTree(content);
                    if (!searchPage.get("isSuccess").asBoolean()) {
                        log.info("Skipping url :{} page :{}", categoryUrl, page);
                        continue;
                    }

                    var productsSearchList = searchPage.get("result").get("products");
                    Set<Long> groupIds = new HashSet<>();
                    Set<Long> productIds = new HashSet<>();
                    productsSearchList.forEach(product -> {
                        Long productGroupId = product.get("productGroupId").longValue();
                        if (groupIdFrags.putIfAbsent(productGroupId, true) != null) {
                            return;
                        }
                        groupIds.add(productGroupId);
                        productIds.add(product.get("id").longValue());
                    });
                    
                    productIds.addAll(extractRelatedProductIds(groupIds));
                    
                    productIds.forEach(productId -> {
                        List<JsonNode> productVariants = extractAllVariants(productId);
                        productVariants.forEach(DataBuilder::build);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            insertFetchedData();
            startFetchProductReviews();
            DataBuilder.productMerchantVariants.clear();
            DataBuilder.productVariants.clear();
            DataBuilder.products.clear();
            DataBuilder.merchants.clear();
        }

        insertFetchedData();
        startFetchProductReviews();
        log.info("TRENDYOL FETCH PRODUCT END");
    }

    private static void insertFetchedData(){

        DataBuilder.categories.values().forEach(DBHelper::insertCategory);
        DataBuilder.brands.values().forEach(DBHelper::insertBrand);
        DataBuilder.merchants.values().forEach(DBHelper::insertMerchant);
        DataBuilder.colors.values().forEach(color -> {
            color.setId(DBHelper.insertColor(color));
        });
        DataBuilder.genders.values().forEach(DBHelper::insertGender);
        DataBuilder.attributeKeys.values().forEach(DBHelper::insertAttributeKeys);
        DataBuilder.attributeValues.values().forEach(DBHelper::insertAttributeValue);
        DataBuilder.productVariantKeys.values().forEach(DBHelper::insertVariantOptionKeys);
        DataBuilder.productVariantValues.values().forEach(attributeValue -> {
            attributeValue.setId(DBHelper.insertVariantOptionValue(attributeValue));
        });
        DataBuilder.products.values().forEach(DBHelper::insertProductPool);
        DataBuilder.productVariants.values().forEach(DBHelper::insertProductPoolVariant);
        DataBuilder.productMerchantVariants.values().forEach(DBHelper::insertProductMerchantVariant);

    }

    private record Category (Long id, String name, Category parent) {}
    private record Brand (Long id, String name) {}
    private record Gender (Long id, String name) {}
    private record AttributeKey (Long id, String name) {}
    private record Address (String cityName, String districtName, String address){}
    private record Merchant (Long id, String name, String officialName, String registeredEmailAddress, Float sellerScore, UrlSchema logoUrl, Address address) {}
    private record ProductVariant (Long id, String barcode, AttributeValue attributeValue, Product product) {}
    private record ProductMerchantVariant (ProductVariant productVariant, Merchant merchant, Float originalPrice, Float discountPrice, Integer quantity) {}
    public record UrlSchema(String root, String folder, String fileName){}
    @AllArgsConstructor
    @Data
    private static class Color{ Long id; String name;}
    @AllArgsConstructor
    @Data
    private static class AttributeValue{ Long id; String name; AttributeKey key;}
    @AllArgsConstructor
    @Data
    private static class Product{
        private Long id;
        private String title;
        private String description;
        private String modelCode;
        private Float rate;
        private String slug;
        private Category category;
        private Gender gender;
        private Color color;
        private Brand brand;
        private List<UrlSchema> images;
        private List<AttributeValue> attributes;
    }

    private static class DataBuilder{
        private static final ConcurrentHashMap<Long, Product> products = new ConcurrentHashMap<>();
        private static final ConcurrentHashMap<Long, ProductVariant> productVariants = new ConcurrentHashMap<>();
        private static final ConcurrentHashMap<String, ProductMerchantVariant> productMerchantVariants = new ConcurrentHashMap<>();
        private static final ConcurrentHashMap<Long, Merchant> merchants = new ConcurrentHashMap<>();
        private static final ConcurrentHashMap<Long, AttributeKey> attributeKeys = new ConcurrentHashMap<>();
        private static final ConcurrentHashMap<Long, AttributeValue> attributeValues = new ConcurrentHashMap<>();
        private static final ConcurrentHashMap<Long, AttributeKey> productVariantKeys = new ConcurrentHashMap<>();
        private static final ConcurrentHashMap<String, AttributeValue> productVariantValues = new ConcurrentHashMap<>();
        private static final ConcurrentHashMap<Long, Gender> genders = new ConcurrentHashMap<>();
        private static final ConcurrentHashMap<String, Color> colors = new ConcurrentHashMap<>();
        private static final ConcurrentHashMap<Long, Brand> brands = new ConcurrentHashMap<>();
        private static final ConcurrentHashMap<Long, Category> categories = new ConcurrentHashMap<>();

        public static void build(JsonNode productJsonNode) {

            Long productId = productJsonNode.get("id").longValue();
            Product product = products.get(productId);
            if (product == null) {
                Category category = makeCategory(productJsonNode.get("webCategoryTree"));
                Brand brand = new Brand(productJsonNode.get("brand").get("id").longValue(), productJsonNode.get("brand").get("name").textValue());
                brand = putIfAbsent(brands, brand.id(), brand);
                Color color = null;
                Gender gender = null;
                Float ratingScore = null;
                List<AttributeValue> subAttributeValues = new ArrayList<>();
                List<UrlSchema> images = new ArrayList<>();
                if (!productJsonNode.get("color").isNull()) {
                    color = new Color(null, Utils.capitalize(productJsonNode.get("color").textValue()));
                    color = putIfAbsent(colors, color.getName(), color);
                }
                if (productJsonNode.get("gender") != null && productJsonNode.get("gender").has("id")) {
                    gender = new Gender(productJsonNode.get("gender").get("id").longValue(), productJsonNode.get("gender").get("name").textValue());
                    gender = putIfAbsent(genders, gender.id, gender);
                }

                for (JsonNode attribute : productJsonNode.get("attributes")) {
                    if (attribute.get("key").get("id").longValue() <=0 || attribute.get("value").get("id").longValue() <= 0){
                        continue;
                    }
                    AttributeKey attributeKey = new AttributeKey(attribute.get("key").get("id").longValue(), attribute.get("key").get("name").textValue());
                    attributeKey = putIfAbsent(attributeKeys, attributeKey.id(), attributeKey);
                    AttributeValue attributeValue = new AttributeValue(attribute.get("value").get("id").longValue(), attribute.get("value").get("name").textValue(), attributeKey);
                    attributeValue = putIfAbsent(attributeValues, attributeValue.getId(), attributeValue);
                    subAttributeValues.add(attributeValue);
                }

                if (productJsonNode.hasNonNull("ratingScore")){
                    if (productJsonNode.get("ratingScore").hasNonNull("averageRating")){
                        ratingScore = productJsonNode.get("ratingScore").get("averageRating").floatValue();
                    }
                }

                StringBuilder description = new StringBuilder();

                for (int i = 0; i < productJsonNode.get("descriptions").size(); i++) {
                    if (i > 0) {
                        description.append(productJsonNode.get("descriptions").get(i).get("text").textValue()).append("\n");
                    }
                }

                for(JsonNode image : productJsonNode.get("images")) {
                    images.add(Utils.toUrlSchema("https://cdn.dsmcdn.com"+ image.textValue()));
                }

                String title = productJsonNode.get("name").textValue();
                product = new Product(productId, title, description.toString(), productJsonNode.get("productCode").textValue(),ratingScore,
                        Utils.toSlug(title, productId.toString()), category, gender, color, brand, images, subAttributeValues);
                products.put(product.getId(), product);
            }

            AttributeValue variantValue = null;
            JsonNode variant = productJsonNode.get("variants").get(0);
            if(variant.has("attributeId") && variant.get("attributeId").longValue() > 0) {
                AttributeKey variantKey = new AttributeKey(variant.get("attributeId").longValue(), variant.get("attributeName").textValue());
                variantKey = putIfAbsent(productVariantKeys,variantKey.id(), variantKey);
                variantValue = new AttributeValue(0L, Utils.capitalize(variant.get("attributeValue").textValue()), variantKey);
                variantValue = putIfAbsent(productVariantValues, variantKey.id()+"_"+variantValue.getName(), variantValue);
            }

            JsonNode merchantListings = productJsonNode.get("merchantListings");
            for (JsonNode merchantList : merchantListings) {

                JsonNode merchantJsonNode = merchantList.get("merchant");

                Address address = new Address(merchantJsonNode.get("cityName").textValue(), merchantJsonNode.get("districtName").textValue(), merchantJsonNode.get("address").textValue());
                Float sellerScore = merchantJsonNode.has("sellerScore") ? merchantJsonNode.get("sellerScore").floatValue() : null;
                String officialName =  !merchantJsonNode.has("officialName") ? merchantJsonNode.get("officialName").textValue() : null;
                String registeredEmailAddress = merchantJsonNode.hasNonNull("registeredEmailAddress") ?  merchantJsonNode.get("registeredEmailAddress").textValue() : null;
                Merchant merchant = new Merchant(merchantJsonNode.get("id").longValue(), merchantJsonNode.get("name").textValue(),
                        officialName, registeredEmailAddress, sellerScore, Utils.toUrlSchema(merchantJsonNode.get("logoUrl").textValue()), address);

                merchant = putIfAbsent(merchants, merchant.id(), merchant);


                JsonNode merchantVar = merchantList.get("variants").get(0);
                JsonNode priceJsonNode = merchantVar.get("price");
                ProductVariant productVariant = new ProductVariant(merchantVar.get("itemNumber").longValue(), merchantVar.get("barcode").textValue(), variantValue, product);
                productVariant = putIfAbsent(productVariants, productVariant.id(), productVariant);

                Integer quantity = new Random().nextInt(80);
                ProductMerchantVariant merchantVariant =new ProductMerchantVariant(productVariant, merchant, priceJsonNode.get("originalPrice").floatValue(),
                        priceJsonNode.get("discountedPrice").floatValue(), quantity);
                putIfAbsent(productMerchantVariants,merchant.id()+"_"+productVariant.id(), merchantVariant);
            }

        }

        private static  <K, V> V putIfAbsent(Map<K, V> map, K key, V value) {
            V exist = map.putIfAbsent(key, value);
            return exist == null ? value : exist;
        }

        private static Category makeCategory(JsonNode webCategoryTree) {
            List<JsonNode> temp = new ArrayList<>();
            webCategoryTree.forEach(temp::add);
            temp.sort(Comparator.comparingInt(jsonNode -> jsonNode.get("level").intValue()));

            Category parentCategory =  null;

            for (JsonNode category : temp) {
                parentCategory = new Category(category.get("id").longValue(), category.get("name").textValue(), parentCategory);
                parentCategory = putIfAbsent(categories, parentCategory.id(), parentCategory);
            }

            return parentCategory;
        }
    }

    private Set<String> extractCategoryUrls() throws JsonProcessingException {
        String content = Utils.get("https://www.trendyol.com/").replace("\n", "").replaceAll("\\s+", " ");
        JsonNode navigationData = objectMapper.readTree(Utils.getBetween(content, "window.__NAVIGATION_APP_INITIAL_STATE_V2__ = ", "<script>"));
        if (navigationData == null || !navigationData.has("items")) {
            throw new IllegalStateException("Trendyol navigation data not found.");
        }

        Set<String> urls = new HashSet<>();
        for (JsonNode items : navigationData.get("items")) {
            for (JsonNode child : items.get("children")) {
                if (child == null || child.isNull() || child.get("children") == null || child.get("children").isNull()) {
                    continue;
                }
                for (JsonNode item : child.get("children")) {
                    urls.add(item.get("webUrl").textValue());
                }
            }
        }

        return urls;
    }

    private Set<Long> extractRelatedProductIds(Set<Long> groupIds) throws JsonProcessingException {
        Set<Long> productIds = new HashSet<>();
        String groupIdUrl = productGroupIdUrl+"?";
        for(Long productGroupId : groupIds){
            groupIdUrl += "productGroupIds="+productGroupId+"&";
        }
        JsonNode jsonNode = objectMapper.readTree(Utils.get(groupIdUrl));
        jsonNode.get("result").forEach(child -> {
            child.forEach(childChild -> {
                productIds.add(childChild.get("id").longValue());
            });
        });
        return productIds;
    }

    private List<JsonNode> extractAllVariants(Long productId){
        List<Future<?>> futures = new ArrayList<>();
        List<JsonNode> jsonNodes = new ArrayList<>();
        try {
            JsonNode jsonNode = objectMapper.readTree(Utils.get(productDetailUrl+productId));
            JsonNode product = jsonNode.get("result");
            JsonNode allVariants = product.get("allVariants");
            jsonNodes.add(product);
            if (allVariants.size() > 1) {
                for (JsonNode variant : allVariants) {
                    futures.add(
                            executor.submit(()-> {
                                String subVariantUrl = productDetailUrl+productId+"?itemNumber="+variant.get("itemNumber").longValue();
                                JsonNode subJsonNode = null;
                                try {
                                    subJsonNode = objectMapper.readTree(Utils.get(subVariantUrl));
                                    jsonNodes.add(subJsonNode.get("result"));
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            })
                    );
                }
            }

            while (futures.stream().filter(Future::isDone).count() != futures.size()) {
                Thread.sleep(100);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return jsonNodes;
    }

    private void insertCustomerUsers(Integer size) {
        if (DBHelper.getRandomIdDB("customer_user") != null) {
            return;
        }
        var random = new Random();
        Faker faker = new Faker();
        for (int i = 0; i < size; i++) {
            var customerUser = new CustomerUserCreateRequestDTO();
            customerUser.setFirstName(faker.name().firstName());
            customerUser.setLastName(faker.name().lastName());
            customerUser.setEmail(random.nextInt(0, 10000) + "." + faker.internet().emailAddress());
            customerUser.setPassword("123456");
            customerUser.setPhone(faker.phoneNumber().phoneNumberInternational().replaceAll("[^0-9]", ""));
            customerUserManager.create(customerUser);
        }
    }

    public void startFetchProductReviews(){

        var productIdList = jdbcTemplate.queryForList("select pp.id from product_pool pp left join product_pool_review ppr on ppr.product_pool_id = pp.id where ppr.id is null limit 50000");
        var reviewsUrl = "https://apigw.trendyol.com/discovery-web-websfxsocialreviewrating-santral/product-reviews-detailed?contentId=%d&order=DESC&orderBy=LastModifiedDate&page=%d";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss XXXXX", Locale.of("tr"));


        var executor = Executors.newVirtualThreadPerTaskExecutor();
        CopyOnWriteArrayList<Future<?>> futures = new CopyOnWriteArrayList<>();
        productIdList.forEach(prod -> {
            futures.add(executor.submit(() -> {
                var pageSize = 1;//random.nextInt(5);
                for (int page = 0; page < pageSize; page++) {
                    try {

                        var searchPageResult = Utils.get(reviewsUrl.formatted(prod.get("id"), page));
                        var searchPage = objectMapper.readTree(searchPageResult);
                        var reviews = searchPage.get("result").get("productReviews");
                        if (reviews.get("size").intValue() < 1) {
                            return;
                        }
                        reviews.get("content").forEach(review -> {
                            var merchantName = review.get("sellerName").textValue();
                            var merchantId = DBHelper.getIdDB("merchant", "name", merchantName);
                            if (merchantId == null) {
                                return;
                            }

                            Long customerUserId = DBHelper.getRandomIdDB("customer_user");

                            var sql = "INSERT INTO product_pool_review (product_pool_id, merchant_id, customer_user_id, rate, text, created_at) VALUES (?,?,?,?,?,?)";
                            DBHelper.insertDB(sql, prod.get("id"), merchantId, customerUserId, review.get("rate").intValue(),
                                    review.get("comment").textValue(), OffsetDateTime.parse(review.get("commentDateISOtype").textValue() + " 00:00:00 +03:00", formatter));

                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }));

            if (futures.size() % 100 == 0) {
                while (futures.stream().filter(Future::isDone).count() != futures.size()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                futures.clear();
            }
        });
    }

    private static class Utils{
        private static final RestTemplate restTemplate = new RestTemplate();


        static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
        static final Pattern WHITESPACE = Pattern.compile("[\\s]");

        public static String get(String url) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json, text/plain, */*");
            headers.set("Accept-Language", "en-US,en;q=0.9");
            headers.set("Origin", "https://www.trendyol.com");
            headers.set("User-Agent", "PostmanRuntime/7.43.0");

            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
        }

        public static String toSlug(String... input) {
            var joinedInput = org.apache.commons.lang3.StringUtils.join(input, " ");
            String noWhitespace = WHITESPACE.matcher(joinedInput).replaceAll("-");
            String normalized = Normalizer.normalize(noWhitespace, Normalizer.Form.NFD);
            String slug = NON_LATIN.matcher(normalized).replaceAll("");
            return slug.toLowerCase(Locale.ENGLISH);
        }

        public static UrlSchema toUrlSchema(String url) {
            try {
                URI uri = new URI(url);
                String path = uri.getPath();
                int lastSlashIndex = path.lastIndexOf('/');
                return new UrlSchema(uri.getScheme() + "://" + uri.getHost(), path.substring(0, lastSlashIndex + 1), path.substring(lastSlashIndex + 1));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        public static String capitalize(String input) {
            if (input == null || input.isEmpty()) {
                return input;
            }
            return StringUtils.capitalize(input.toLowerCase(Locale.of("tr")));
        }

        public static String getBetween(String content, String start, String end) {

            Pattern pattern = Pattern.compile(start + "(.*?)" + end, Pattern.DOTALL);
            Matcher matcher = pattern.matcher(content);

            if (matcher.find()) {
                return matcher.group(1).trim().replaceAll(";$", "");
            }

            throw new RuntimeException("Content NOT contains between: " + start + " " + end + "->" + content);
        }
        
    }
    
    private static class DBHelper{
        private static JdbcTemplate jdbcTemplate;
        
        private static void init(JdbcTemplate jdbcTemplate){
            DBHelper.jdbcTemplate = jdbcTemplate;
        }

        private static void insertCategory(Category category){

            Long parentId = null;
            if (category.parent() != null) {
                parentId = category.parent.id();
                insertCategory(category.parent());
            }

            if (DBHelper.existsDB("category", category.id())) {
                return;
            }

            var sql = "INSERT INTO category (id, name, parent_id) VALUES (?, ?, ?)";
            DBHelper.insertDB(sql, category.id(), category.name(), parentId);
        }

        private static void insertBrand(Brand brand) {
            if (DBHelper.existsDB("brand", brand.id())) {
                return;
            }
            var sql = "INSERT INTO brand (id, name) VALUES (?, ?)";
            DBHelper.insertDB(sql, brand.id(), brand.name());
        }

        private static void insertMerchant(Merchant merchant){

            try {
                if (DBHelper.existsDB("merchant", merchant.id())) {
                    return;
                }
                Long logoImageId = null;
                Long addressId = null;

                if (merchant.logoUrl() != null) {
                    var logo = merchant.logoUrl();
                    logoImageId = DBHelper.insertDB(
                            "INSERT INTO general_image(root, folder, file_name, large_file_name) VALUES (?,?,?,?)",
                            logo.root(), logo.folder(), logo.fileName(), logo.fileName());
                }

                if (merchant.address() != null) {
                    var address = merchant.address();
                    var district = DBHelper.findDistrictByNameWithCityName(address.districtName(), address.cityName());

                    if (district != null) {
                        addressId = DBHelper.insertDB(
                                "INSERT INTO address(title, district_id, city_id, country_id, text) VALUES (?,?,?,?,?)",
                                merchant.officialName() == null ? merchant.id() : merchant.officialName(), district.get("id"), district.get("city_id"), district.get("country_id"), address.address());
                    }
                }

                var sql = "INSERT INTO merchant(id, name, official_name, email, rate, status_id, address_id, logo_image_id) VALUES (?,?,?,?,?,?,?,?)";
                DBHelper.insertDB(sql, merchant.id(), merchant.name(), merchant.officialName(), merchant.registeredEmailAddress(),
                        merchant.sellerScore(), MerchantStatus.Status.ACTIVE.getValue(), addressId, logoImageId);
            } catch (Exception e) {
               e.printStackTrace();
            }
        }

        public static Long insertColor(Color color) {
            var colorId = DBHelper.getIdDB("product_pool_color", "name", color.getName());
            if (colorId != null) {
                return colorId;
            }
            var sql = "INSERT INTO product_pool_color (name) VALUES (?)";
            return DBHelper.insertDB(sql, color.getName());
        }

        private static void insertGender(Gender gender) {
            if (DBHelper.existsDB("product_pool_gender", gender.id())) {
                return;
            }

            var sql = "INSERT INTO product_pool_gender(id, name) VALUES (?,?)";
            DBHelper.insertDB(sql, gender.id(), gender.name());
        }

        private static void insertAttributeKeys(AttributeKey key) {
            if (DBHelper.existsDB("product_pool_attribute", key.id())) {
                return;
            }

            var sql = "INSERT INTO product_pool_attribute (id, name) VALUES (?,?)";
            DBHelper.insertDB(sql, key.id(), key.name());
        }

        private static void insertAttributeValue(AttributeValue value) {

            if (DBHelper.existsDB("product_pool_attribute_value", value.getId())) {
                return;
            }
            String sql = "INSERT INTO product_pool_attribute_value (id, name, attribute_id) VALUES (?, ?, ?)";
            DBHelper.insertDB(sql, value.getId(), value.getName(), value.getKey().id());
        }

        private static void insertVariantOptionKeys(AttributeKey key) {
            if (DBHelper.existsDB("product_pool_variant_option", key.id())) {
                return;
            }

            var sql = "INSERT INTO product_pool_variant_option (id, name) VALUES (?,?)";
            DBHelper.insertDB(sql, key.id(), key.name());
        }

        private static Long insertVariantOptionValue(AttributeValue value) {

            String sql = null;

            if (value.getId() == null || value.getId() < 1) {
                Long valueId = jdbcTemplate.query("SELECT id FROM product_pool_variant_option_value WHERE name = ? and option_id=?", rs-> {
                    return rs.next() ? rs.getLong("id") : null;
                }, value.getName(), value.getKey().id());
                if (valueId != null) {
                    return valueId;
                }
                sql = "INSERT INTO product_pool_variant_option_value (name, option_id) VALUES (?, ?)";
                return DBHelper.insertDB(sql, value.getName(), value.getKey().id());
            }else {
                if (DBHelper.existsDB("product_pool_variant_option_value", value.getId())) {
                    return value.getId();
                }
                sql = "INSERT INTO product_pool_variant_option_value (id, name, option_id) VALUES (?, ?, ?)";
                return DBHelper.insertDB(sql, value.getId(), value.getName(), value.getKey().id());
            }

        }

        public static void insertProductPool(Product product) {
            if(DBHelper.existsDB("product_pool", product.getId())) {
                return;
            }
            String sql = """
                INSERT INTO product_pool (id, title, model_code, rate, description,gender_id, category_id, color_id, brand_id, state_id, slug)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";
            DBHelper.insertDB(sql, product.getId(), product.getTitle(), product.getModelCode(), product.getRate(), product.getDescription(),product.getGender() != null ? product.getGender().id() : null,
                    product.getCategory().id(), product.getColor() != null ? product.getColor().getId() : null, product.getBrand().id(), ProductPoolState.State.ACTIVE.getValue(), product.getSlug());

            for (var image : product.getImages()) {
                DBHelper.insertDB("INSERT INTO product_pool_image (product_pool_id, root, folder, file_name, large_file_name) VALUES (?,?,?,?,?)",
                        product.getId(), image.root(), image.folder(), "1_org.jpg", image.fileName());
            }


            product.attributes.forEach(attribute -> {
                DBHelper.insertDB("INSERT INTO product_pool_attribute_value_map (product_pool_id, attribute_values_id) VALUES (?,?)",
                        product.getId(), attribute.getId());
            });
        }

        public static void insertProductPoolVariant(ProductVariant variant) {
            if (DBHelper.existsDB("product_pool_variant", variant.id())) {
                return;
            }
            String sql = "INSERT INTO product_pool_variant (id, barcode, product_pool_id, option_value_id) values (?, ?, ?, ?)";

            DBHelper.insertDB(sql, variant.id(), variant.barcode(), variant.product().getId(), variant.attributeValue() != null ? variant.attributeValue().getId() : null);

        }

        public static void insertProductMerchantVariant(TrendyolFinalCommand.ProductMerchantVariant merchantVariant){

            String sql = "INSERT INTO product_merchant_variant (product_pool_variant_id, merchant_id, original_price, discounted_price, stock_quantity)\n" +
                    "VALUES (?, ?, ?, ?, ?)";

            DBHelper.insertDB(sql, merchantVariant.productVariant().id(), merchantVariant.merchant().id(), merchantVariant.originalPrice(),
                    merchantVariant.discountPrice(), merchantVariant.quantity());
        }

        private static Map<String, Long> findDistrictByNameWithCityName(String districtName, String cityName) throws SQLException {
            String sql = "SELECT d.id, d.city_id, c.country_id FROM district d INNER JOIN city c ON d.city_id=c.id WHERE d.name=? AND c.name=? LIMIT 1";
            return   jdbcTemplate.query(sql, re-> {
                if (re.next()) {
                    var map = new HashMap<String, Long>();
                    map.put("id", re.getLong("id"));
                    map.put("city_id", re.getLong("city_id"));
                    map.put("country_id", re.getLong("country_id"));
                    return map;
                }
                return null;
                }, districtName, cityName);
        }
        
        private static Long insertDB(String sql, Object... params) {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() == null) {
                return null;
            }
            return keyHolder.getKey().longValue();
        }

        public static Boolean existsDB(String tableName,  String fieldName, Object fieldValue) {
            return getIdDB(tableName, fieldName, fieldValue) != null ;
        }
        public static Boolean existsDB(String tableName, Long id) {
            return existsDB(tableName, "id", id);
        }
        public static Long getIdDB(String tableName,  String fieldName, Object fieldValue) {
            return jdbcTemplate.query("select id from " + tableName + " where " + fieldName + " = ? limit 1", rs ->{
                return rs.next() ? rs.getLong("id") : null;
            }, fieldValue.toString());
        }

        public static Long getRandomIdDB(String tableName) {
            return jdbcTemplate.query("select id from " + tableName + " order by rand()  limit 1", rs ->{
                return rs.next() ? rs.getLong("id") : null;
            });
        }
    }
}
