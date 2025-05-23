package com.marketplaces.core.repository;

import com.marketplaces.core.entity.ProductPoolVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPoolVariantRepository extends JpaRepository<ProductPoolVariant, Long> {
}
