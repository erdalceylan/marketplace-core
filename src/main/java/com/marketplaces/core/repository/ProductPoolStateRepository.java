package com.marketplaces.core.repository;

import com.marketplaces.core.entity.ProductPoolState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPoolStateRepository extends JpaRepository<ProductPoolState, Short> {
}
