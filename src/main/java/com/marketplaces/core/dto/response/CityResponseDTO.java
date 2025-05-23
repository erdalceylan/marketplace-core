package com.marketplaces.core.dto.response;

import lombok.Data;

@Data
public class CityResponseDTO {
    private Integer id;
    private String name;
    private CountryResponseDTO country;
}
