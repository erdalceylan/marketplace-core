package com.marketplaces.core.service;

import com.marketplaces.core.entity.ProductPoolColor;
import com.marketplaces.core.repository.ProductPoolColorRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductPoolColorService {

    private final ProductPoolColorRepository productPoolColorRepository;
    public ProductPoolColorService(ProductPoolColorRepository productPoolColorRepository) {
        this.productPoolColorRepository = productPoolColorRepository;
    }

    public ProductPoolColor save(ProductPoolColor productPoolColor) {
        return productPoolColorRepository.save(productPoolColor);
    }

    public ProductPoolColor findById(Short id) {
        return productPoolColorRepository.findById(id).orElse(null);
    }

    public ProductPoolColor findByName(String name) {
        return productPoolColorRepository.findByName(name);
    }
}
