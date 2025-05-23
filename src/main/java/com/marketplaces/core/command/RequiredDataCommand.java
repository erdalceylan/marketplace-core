package com.marketplaces.core.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplaces.core.entity.*;
import com.marketplaces.core.repository.CustomerUserStatusRepository;
import com.marketplaces.core.repository.MerchantStatusRepository;
import com.marketplaces.core.repository.MerchantUserStatusRepository;
import com.marketplaces.core.repository.ProductPoolStateRepository;
import com.marketplaces.core.service.CountryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Set;

@Component
@Order(1)
public class RequiredDataCommand implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final CountryService countryService;
    private final JdbcTemplate jdbcTemplate;
    private final ProductPoolStateRepository productPoolStateRepository;
    private final CustomerUserStatusRepository customerUserStatusRepository;
    private final MerchantStatusRepository merchantStatusRepository;
    private final MerchantUserStatusRepository merchantUserStatusRepository;

    RequiredDataCommand(
            ObjectMapper objectMapper,
            CountryService countryService,
            JdbcTemplate jdbcTemplate,
            ProductPoolStateRepository productPoolStateRepository,
            CustomerUserStatusRepository customerUserStatusRepository,
            MerchantStatusRepository merchantStatusRepository,
            MerchantUserStatusRepository merchantUserStatusRepository
    ){
        this.objectMapper = objectMapper;
        this.countryService = countryService;
        this.jdbcTemplate = jdbcTemplate;

        this.productPoolStateRepository = productPoolStateRepository;
        this.customerUserStatusRepository = customerUserStatusRepository;
        this.merchantStatusRepository = merchantStatusRepository;
        this.merchantUserStatusRepository = merchantUserStatusRepository;
    }

    @Override
    public void run(String... args){

        Set<String> argList = Set.of(args);

        if (argList.isEmpty() || !argList.contains("Fill-Required-Data")) {
            return;
        }

        fillCountries();
        fillTypes();
    }

    private void fillCountries(){
        if (this.countryService.findByCountryCode("TR") != null) {
            return;
        }
        try {
            var countries = objectMapper.readTree(new URI("https://restcountries.com/v3.1/all").toURL());
            for (JsonNode countryJsonItem : countries) {
                var country = new Country();
                country.setCode(countryJsonItem.get("cca2").textValue());
                country.setName(countryJsonItem.get("translations").get("tur").get("common").textValue());
                countryService.save(country);
            }

            var cities = objectMapper.readTree(new URI("https://turkiyeapi.dev/api/v1/provinces").toURL());
            var country = this.countryService.findByCountryCode("TR");
            for (JsonNode cityJsonItem : cities.get("data")) {

                insertCity(
                        cityJsonItem.get("id").intValue(),
                        cityJsonItem.get("name").textValue(),
                        country.getId());

                for (JsonNode districtJsonItem : cityJsonItem.get("districts")) {
                    insertDistrict(
                            districtJsonItem.get("id").intValue(),
                            districtJsonItem.get("name").textValue(),
                            cityJsonItem.get("id").intValue());
                }
            }
        } catch (RuntimeException | URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private void insertCity(Integer id, String cityName, Short countryId) {
        String sql = "insert into city (id, name, country_id) values (?,?,?)";
        jdbcTemplate.update(sql,id,cityName,countryId);
    }

    private void insertDistrict(Integer id, String districtName, Integer cityId) {
        String sql = "insert into district (id, name, city_id) values (?,?,?)";
        jdbcTemplate.update(sql,id,districtName,cityId);
    }

    private void fillTypes(){
        if (productPoolStateRepository.findById(ProductPoolState.State.ACTIVE.getValue()).isEmpty()) {
            Arrays.stream(ProductPoolState.State.class.getEnumConstants()).forEach(state -> {
                var productPoolState = new ProductPoolState();
                productPoolState.setId(state.getValue());
                productPoolState.setName(state.name());
                productPoolStateRepository.save(productPoolState);
            });
        }

        if (customerUserStatusRepository.findById(CustomerUserStatus.Status.ACTIVE.getValue()).isEmpty()) {
            Arrays.stream(CustomerUserStatus.Status.class.getEnumConstants()).forEach(status -> {
                var customerUserStatus = new CustomerUserStatus();
                customerUserStatus.setId(status.getValue());
                customerUserStatus.setName(status.name());
                customerUserStatusRepository.save(customerUserStatus);
            });
        }

        if (merchantUserStatusRepository.findById(MerchantUserStatus.Status.ACTIVE.getValue()).isEmpty()) {
            Arrays.stream(MerchantUserStatus.Status.class.getEnumConstants()).forEach(status -> {
                var merchantUserStatus = new MerchantUserStatus();
                merchantUserStatus.setId(status.getValue());
                merchantUserStatus.setName(status.name());
                merchantUserStatusRepository.save(merchantUserStatus);
            });
        }

        if (merchantStatusRepository.findById(MerchantStatus.Status.ACTIVE.getValue()).isEmpty()) {
            Arrays.stream(MerchantStatus.Status.class.getEnumConstants()).forEach(status-> {
                var merchantStatus = new MerchantStatus();
                merchantStatus.setId(status.getValue());
                merchantStatus.setName(status.name());
                merchantStatusRepository.save(merchantStatus);
            });
        }
    }

}
