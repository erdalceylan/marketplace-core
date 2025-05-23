package com.marketplaces.core.dto.response;

import lombok.Data;

@Data
public class ProductPoolAttributeValueResponseDTO {
    private Long id;
    private String name;
    private ProductPoolAttributeResponseDTO attribute;
}
