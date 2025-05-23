package com.marketplaces.core.repository;


import com.marketplaces.core.entity.ProductPoolVariantOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPoolVariantOptionRepository extends JpaRepository<ProductPoolVariantOption, Long> {
}
