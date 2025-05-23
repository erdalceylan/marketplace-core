package com.marketplaces.core.service;

import com.marketplaces.core.entity.ProductPoolVariant;
import com.marketplaces.core.repository.ProductPoolVariantRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProductPoolVariantService {

    private final ProductPoolVariantRepository productPoolVariantRepository;

    public ProductPoolVariantService(
            ProductPoolVariantRepository productPoolVariantRepository
    ) {
        this.productPoolVariantRepository = productPoolVariantRepository;
    }

    public ProductPoolVariant findById(Long id) {
        return this.productPoolVariantRepository.findById(id).orElse(null);
    }

    public ProductPoolVariant save(ProductPoolVariant productPoolVariant) {
        return this.productPoolVariantRepository.save(productPoolVariant);
    }
}
