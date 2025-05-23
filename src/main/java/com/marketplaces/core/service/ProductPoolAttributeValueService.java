package com.marketplaces.core.service;

import com.marketplaces.core.entity.ProductPoolAttributeValue;
import com.marketplaces.core.repository.ProductPoolAttributeValueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPoolAttributeValueService {

    private final ProductPoolAttributeValueRepository productPoolAttributeValueRepository;

    public ProductPoolAttributeValueService(ProductPoolAttributeValueRepository productPoolAttributeValueRepository) {
        this.productPoolAttributeValueRepository = productPoolAttributeValueRepository;
    }

    public ProductPoolAttributeValue save(ProductPoolAttributeValue productPoolAttributeValue) {
        return productPoolAttributeValueRepository.save(productPoolAttributeValue);
    }

    public ProductPoolAttributeValue findById(Long id) {
        return productPoolAttributeValueRepository.findById(id).orElse(null);
    }

    public List<ProductPoolAttributeValue> findByIds(Iterable<Long> id) {
        return productPoolAttributeValueRepository.findAllById(id);
    }
}
