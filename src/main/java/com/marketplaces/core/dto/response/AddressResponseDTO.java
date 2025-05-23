package com.marketplaces.core.dto.response;

import lombok.Data;

@Data
public class AddressResponseDTO {

    private Long id;
    private String title;
    private String text;
    private CountryResponseDTO country;
    private CityResponseDTO city;
    private DistrictResponseDTO district;
}
