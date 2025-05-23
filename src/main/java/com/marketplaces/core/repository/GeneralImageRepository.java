package com.marketplaces.core.repository;

import com.marketplaces.core.entity.GeneralImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralImageRepository extends JpaRepository<GeneralImage, Long> {
}
