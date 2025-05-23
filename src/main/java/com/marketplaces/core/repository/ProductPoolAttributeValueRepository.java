package com.marketplaces.core.repository;

import com.marketplaces.core.entity.ProductPoolAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPoolAttributeValueRepository extends JpaRepository<ProductPoolAttributeValue, Long> {
}
