package com.marketplaces.core.repository;

import com.marketplaces.core.entity.ProductPoolReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPoolReviewRepository extends JpaRepository<ProductPoolReview, Long> {
}
