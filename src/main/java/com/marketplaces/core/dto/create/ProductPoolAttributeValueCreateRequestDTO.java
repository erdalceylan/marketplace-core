package com.marketplaces.core.dto.create;

import lombok.Data;

@Data
public class ProductPoolAttributeValueCreateRequestDTO {
    private String name;
    private Long parentId;
}
