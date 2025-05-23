package com.marketplaces.core.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marketplaces.core.entity.ProductPool;
import com.marketplaces.core.manager.ProductPoolManager;
import com.marketplaces.core.repository.ProductPoolRepository;
import com.marketplaces.core.service.KafkaProducerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Stream;

@Component
@Order(3)
public class ProductSendKafkaForElasticCommand implements CommandLineRunner {


    private final ProductPoolManager productPoolManager;
    private final KafkaProducerService kafkaProducerService;
    private final ProductPoolRepository productPoolRepository;

    public ProductSendKafkaForElasticCommand(
            ProductPoolManager productPoolManager,
            KafkaProducerService kafkaProducerService,
            ProductPoolRepository productPoolRepository
    ){
        this.productPoolManager = productPoolManager;
        this.kafkaProducerService = kafkaProducerService;
        this.productPoolRepository = productPoolRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public void run(String... args) {

        Set<String> argList = Set.of(args);
        if (argList.isEmpty() || !argList.contains("Product-Send-Kafka-For-Elastic")) {
            return;
        }

        try (Stream<ProductPool> productPoolStream = productPoolRepository.streamAllProducts()){
            productPoolStream.forEach(productPool -> {
                var responseDTO = productPoolManager.convertRevertFull(productPool);
                try {
                    kafkaProducerService.addUpdateProduct(responseDTO);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
