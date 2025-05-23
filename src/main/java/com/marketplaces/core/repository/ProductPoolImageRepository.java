package com.marketplaces.core.repository;

import com.marketplaces.core.entity.GeneralImage;
import com.marketplaces.core.entity.ProductPoolImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPoolImageRepository extends JpaRepository<ProductPoolImage, Long> {
}
