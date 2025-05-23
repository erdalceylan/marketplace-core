package com.marketplaces.core.repository;

import com.marketplaces.core.entity.ProductMerchantVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMerchantVariantRepository extends JpaRepository<ProductMerchantVariant, Long> {
}
