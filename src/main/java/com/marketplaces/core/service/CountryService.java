package com.marketplaces.core.service;


import com.marketplaces.core.entity.Country;
import com.marketplaces.core.repository.CountryRepository;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    private final CountryRepository countryRepository;
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country save(Country country) {
        return countryRepository.save(country);
    }

    public Country get(Short id) {
        return countryRepository.findById(id).orElse(null);
    }

    public Country findByCountryCode(String code) {
        return countryRepository.findByCode(code);
    }
}
