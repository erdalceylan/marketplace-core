package com.marketplaces.core.repository;

import com.marketplaces.core.entity.Brand;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Query("SELECT b FROM Brand b ORDER BY b.id ASC")
    List<Brand> getTopList(Pageable pageable);
}
