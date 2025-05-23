package com.marketplaces.core.service;

import com.marketplaces.core.entity.ProductPoolAttribute;
import com.marketplaces.core.repository.ProductPoolAttributeRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductPoolAttributeService {

    private final ProductPoolAttributeRepository productPoolAttributeRepository;
    public ProductPoolAttributeService(ProductPoolAttributeRepository productPoolAttributeRepository) {
        this.productPoolAttributeRepository = productPoolAttributeRepository;
    }

    public ProductPoolAttribute save(ProductPoolAttribute productPoolAttribute) {
        return productPoolAttributeRepository.save(productPoolAttribute);
    }

    public ProductPoolAttribute findById(Long id) {
        return productPoolAttributeRepository.findById(id).orElse(null);
    }
}
