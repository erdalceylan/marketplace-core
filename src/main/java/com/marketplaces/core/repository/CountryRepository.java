package com.marketplaces.core.repository;

import com.marketplaces.core.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Short> {

    public Country findByCode(String countryCode);
}
