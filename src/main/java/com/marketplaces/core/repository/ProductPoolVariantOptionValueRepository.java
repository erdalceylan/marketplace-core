package com.marketplaces.core.repository;


import com.marketplaces.core.entity.ProductPoolVariantOptionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPoolVariantOptionValueRepository extends JpaRepository<ProductPoolVariantOptionValue, Long> {
}
