package com.marketplaces.core.repository;

import com.marketplaces.core.entity.ProductPoolColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPoolColorRepository extends JpaRepository<ProductPoolColor, Short> {
    public ProductPoolColor findByName(String name);
}
