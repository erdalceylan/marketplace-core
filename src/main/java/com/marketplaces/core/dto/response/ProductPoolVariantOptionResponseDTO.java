package com.marketplaces.core.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductPoolVariantOptionResponseDTO {
    private Long id;
    private String name;
    private List<ProductPoolVariantOptionValueResponseDTO> values;
}
