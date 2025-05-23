package com.marketplaces.core.service;

import com.marketplaces.core.entity.ProductPoolVariantOption;
import com.marketplaces.core.repository.ProductPoolVariantOptionRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductPoolVariantOptionService {

    private final ProductPoolVariantOptionRepository productPoolVariantOptionRepository;

    public ProductPoolVariantOptionService(ProductPoolVariantOptionRepository productPoolVariantOptionRepository) {
        this.productPoolVariantOptionRepository = productPoolVariantOptionRepository;
    }

    public ProductPoolVariantOption save(ProductPoolVariantOption productPoolAttribute) {
        return productPoolVariantOptionRepository.save(productPoolAttribute);
    }

    public ProductPoolVariantOption findById(Long id) {
        return productPoolVariantOptionRepository.findById(id).orElse(null);
    }
}
