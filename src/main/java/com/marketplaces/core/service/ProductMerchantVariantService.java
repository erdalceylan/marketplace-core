package com.marketplaces.core.service;

import com.marketplaces.core.entity.ProductMerchantVariant;
import com.marketplaces.core.repository.ProductMerchantVariantRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductMerchantVariantService {

    private final ProductMerchantVariantRepository productMerchantVariantRepository;
    public ProductMerchantVariantService(ProductMerchantVariantRepository productMerchantVariantRepository) {
        this.productMerchantVariantRepository = productMerchantVariantRepository;
    }

    public ProductMerchantVariant save(ProductMerchantVariant productMerchantVariant) {
        return productMerchantVariantRepository.save(productMerchantVariant);
    }

    public ProductMerchantVariant findById(Long id) {
        return productMerchantVariantRepository.findById(id).orElse(null);
    }
}
