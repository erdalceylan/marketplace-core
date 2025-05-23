package com.marketplaces.core.repository;

import com.marketplaces.core.entity.City;
import com.marketplaces.core.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {

    @Query("SELECT d FROM District d INNER JOIN d.city ci INNER JOIN ci.country co WHERE d.name=:name AND ci.name=:cityName")
    public District findDistrictByNameWithCityName(String name, String cityName);
}
