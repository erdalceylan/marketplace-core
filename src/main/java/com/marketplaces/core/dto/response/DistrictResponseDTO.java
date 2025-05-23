package com.marketplaces.core.dto.response;

import lombok.Data;

@Data
public class DistrictResponseDTO {
    private Integer id;
    private String name;
    private CityResponseDTO city;
}
