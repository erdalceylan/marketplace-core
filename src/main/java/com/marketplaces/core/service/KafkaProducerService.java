package com.marketplaces.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplaces.core.dto.response.ProductPoolResponseDTO;
import com.marketplaces.core.dto.response.SuggestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${kafka.product-topic}")
    private String productTopic;

    @Value("${kafka.suggest-topic}")
    private String suggestTopic;

    public KafkaProducerService(
            ObjectMapper objectMapper,
            KafkaTemplate<String, String> kafkaTemplate
    ) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void addUpdateProduct(ProductPoolResponseDTO productPoolResponseDTO) throws JsonProcessingException {
        kafkaTemplate.send(productTopic, objectMapper.writeValueAsString(productPoolResponseDTO));
    }

    public void addUpdateSuggest(SuggestDTO suggestDTO) throws JsonProcessingException {
        kafkaTemplate.send(suggestTopic, objectMapper.writeValueAsString(suggestDTO));
    }
}
