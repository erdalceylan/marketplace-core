package com.marketplaces.core.dto.response;

import lombok.Data;

@Data
public class ProductPoolVariantOptionValueResponseDTO {
    private Long id;
    private String name;
    private ProductPoolVariantOptionResponseDTO option;
}
