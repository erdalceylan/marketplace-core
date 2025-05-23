package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.response.CountryResponseDTO;
import com.marketplaces.core.entity.Country;
import org.springframework.stereotype.Service;

@Service
public class CountryMapper {
    public CountryResponseDTO convert(Country country) {
        CountryResponseDTO countryResponseDTO = new CountryResponseDTO();
        countryResponseDTO.setId(country.getId());
        countryResponseDTO.setName(country.getName());
        return countryResponseDTO;
    }
}
