package com.marketplaces.core.service;

import com.marketplaces.core.entity.ProductPoolVariantOptionValue;
import com.marketplaces.core.repository.ProductPoolVariantOptionValueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPoolVariantOptionValueService {


    private final ProductPoolVariantOptionValueRepository productPoolVariantOptionValueRepository;

    public ProductPoolVariantOptionValueService(ProductPoolVariantOptionValueRepository productPoolVariantOptionValueRepository) {
        this.productPoolVariantOptionValueRepository = productPoolVariantOptionValueRepository;
    }

    public ProductPoolVariantOptionValue save(ProductPoolVariantOptionValue value) {
        return productPoolVariantOptionValueRepository.save(value);
    }

    public ProductPoolVariantOptionValue findById(Long id) {
        return productPoolVariantOptionValueRepository.findById(id).orElse(null);
    }

    public List<ProductPoolVariantOptionValue> findByIds(Iterable<Long> id) {
        return productPoolVariantOptionValueRepository.findAllById(id);
    }
}
