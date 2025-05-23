package com.marketplaces.core.service;

import com.marketplaces.core.entity.ProductPool;
import com.marketplaces.core.repository.ProductPoolRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductPoolService {

    private final ProductPoolRepository productPoolRepository;

    public ProductPoolService(
            ProductPoolRepository productPoolRepository
    ){
        this.productPoolRepository = productPoolRepository;
    }

    public ProductPool save(ProductPool productPool) {
        return productPoolRepository.save(productPool);
    }

    public ProductPool findById(Long id) {
        return productPoolRepository.findById(id).orElse(null);
    }

    public ProductPool getReference(Long id) {
        return productPoolRepository.getReferenceById(id);
    }
}
