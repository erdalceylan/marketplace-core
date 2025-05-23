package com.marketplaces.core.service;

import com.marketplaces.core.entity.City;
import com.marketplaces.core.repository.CityRepository;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private final CityRepository cityRepository;
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City save(City city) {
        return cityRepository.save(city);
    }

    public City get(Integer id) {
        return cityRepository.findById(id).orElse(null);
    }
}
