package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.response.CityResponseDTO;
import com.marketplaces.core.entity.City;
import org.springframework.stereotype.Service;

@Service
public class CityMapper {

    private final CountryMapper countryMapper;

    public CityMapper(CountryMapper countryMapper) {
        this.countryMapper = countryMapper;
    }

    public CityResponseDTO convert(City city) {
        CityResponseDTO cityResponseDTO = convertWithoutCountry(city);
        if (city.getCountry() != null) {
            cityResponseDTO.setCountry(countryMapper.convert(city.getCountry()));
        }
        return cityResponseDTO;
    }

    public CityResponseDTO convertWithoutCountry(City city) {
        CityResponseDTO cityResponseDTO = new CityResponseDTO();
        cityResponseDTO.setId(city.getId());
        cityResponseDTO.setName(city.getName());
        return cityResponseDTO;
    }

}
