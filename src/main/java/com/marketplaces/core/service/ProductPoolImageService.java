package com.marketplaces.core.service;

import com.marketplaces.core.entity.ProductPoolImage;
import com.marketplaces.core.repository.ProductPoolImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductPoolImageService {

    private final ProductPoolImageRepository productPoolImageRepository;
    public ProductPoolImageService(ProductPoolImageRepository productPoolImageRepository) {
        this.productPoolImageRepository = productPoolImageRepository;
    }

    public ProductPoolImage save(ProductPoolImage productPoolImage) {
        return productPoolImageRepository.save(productPoolImage);
    }

    public ProductPoolImage get(Long id) {
        return productPoolImageRepository.findById(id).orElse(null);
    }
}
