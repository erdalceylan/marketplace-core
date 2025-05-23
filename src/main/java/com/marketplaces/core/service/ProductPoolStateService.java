package com.marketplaces.core.service;

import com.marketplaces.core.entity.ProductPoolState;
import com.marketplaces.core.repository.ProductPoolStateRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProductPoolStateService {

    private final ProductPoolStateRepository productPoolStateRepository;

    public ProductPoolStateService(
            ProductPoolStateRepository productPoolStateRepository
    ) {
        this.productPoolStateRepository = productPoolStateRepository;
    }

    public ProductPoolState findById(Short id) {
        return this.productPoolStateRepository.findById(id).orElse(null);
    }

    public ProductPoolState save(ProductPoolState productPoolState) {
        return this.productPoolStateRepository.save(productPoolState);
    }
}
