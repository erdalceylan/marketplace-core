package com.marketplaces.core.repository;

import com.marketplaces.core.entity.City;
import com.marketplaces.core.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
}
