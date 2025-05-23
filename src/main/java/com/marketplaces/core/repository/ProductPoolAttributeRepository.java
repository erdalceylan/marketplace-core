package com.marketplaces.core.repository;

import com.marketplaces.core.entity.ProductPoolAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPoolAttributeRepository extends JpaRepository<ProductPoolAttribute, Long> {
}
