package com.marketplaces.core.service;


import com.marketplaces.core.entity.ProductPoolReview;
import com.marketplaces.core.repository.ProductPoolReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductPoolReviewService {


    private final ProductPoolReviewRepository productPoolReviewRepository;

    public ProductPoolReviewService(
            ProductPoolReviewRepository productPoolReviewRepository
    ){
        this.productPoolReviewRepository = productPoolReviewRepository;
    }

    public ProductPoolReview save(ProductPoolReview productPoolReview) {
        return productPoolReviewRepository.save(productPoolReview);
    }

    public ProductPoolReview getReference(Long id) {
        return productPoolReviewRepository.getReferenceById(id);
    }
}
