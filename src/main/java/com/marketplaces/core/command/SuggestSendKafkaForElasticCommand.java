package com.marketplaces.core.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marketplaces.core.dto.response.SuggestDTO;
import com.marketplaces.core.entity.Brand;
import com.marketplaces.core.entity.Category;
import com.marketplaces.core.entity.Merchant;
import com.marketplaces.core.entity.ProductPool;
import com.marketplaces.core.manager.ProductPoolManager;
import com.marketplaces.core.mapper.SuggestMapper;
import com.marketplaces.core.repository.BrandRepository;
import com.marketplaces.core.repository.CategoryRepository;
import com.marketplaces.core.repository.MerchantRepository;
import com.marketplaces.core.repository.ProductPoolRepository;
import com.marketplaces.core.service.KafkaProducerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Component
@Order(3)
public class SuggestSendKafkaForElasticCommand implements CommandLineRunner {


    private final KafkaProducerService kafkaProducerService;
    private final ProductPoolRepository productPoolRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final MerchantRepository merchantRepository;
    private final SuggestMapper suggestMapper;

    public SuggestSendKafkaForElasticCommand(
            KafkaProducerService kafkaProducerService,
            ProductPoolRepository productPoolRepository,
            BrandRepository brandRepository,
            CategoryRepository categoryRepository,
            MerchantRepository merchantRepository,
            SuggestMapper suggestMapper
    ){
        this.kafkaProducerService = kafkaProducerService;
        this.productPoolRepository = productPoolRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.merchantRepository = merchantRepository;
        this.suggestMapper = suggestMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public void run(String... args) throws Exception {

        Set<String> argList = Set.of(args);
        if (argList.isEmpty() || !argList.contains("Suggest-Send-Kafka-For-Elastic")) {
            return;
        }

        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        for (var category : categories){
            var suggestDTO = suggestMapper.convert(category.getId().longValue(), SuggestDTO.Type.CATEGORY, category.getName());
            kafkaProducerService.addUpdateSuggest(suggestDTO);
        }

        List<Brand> brands = brandRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        for (var brand : brands){
            var suggestDTO = suggestMapper.convert(brand.getId().longValue(), SuggestDTO.Type.BRAND, brand.getName());
            kafkaProducerService.addUpdateSuggest(suggestDTO);
        }

        List<Merchant> merchants = merchantRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        for (var merchant : merchants){
            var suggestDTO = suggestMapper.convert(merchant.getId(), SuggestDTO.Type.MERCHANT, merchant.getName());
            kafkaProducerService.addUpdateSuggest(suggestDTO);
        }


        try (Stream<ProductPool> productPoolStream = productPoolRepository.streamAllProducts()){
            productPoolStream.forEach(productPool -> {
                var suggestDTO = suggestMapper.convert(null, SuggestDTO.Type.TEXT, productPool.getTitle());
                try {
                    kafkaProducerService.addUpdateSuggest(suggestDTO);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            });
        }
    }

}
