package com.marketplaces.core.dto.create;

import lombok.Data;

@Data
public class BrandCreateRequestDTO {
    private Integer id;
    private String name;
    private String fullName;
}
