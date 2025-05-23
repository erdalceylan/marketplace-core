package com.marketplaces.core.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class ProductPoolAttributeResponseDTO {
    private Long id;
    private String name;
    private List<ProductPoolAttributeValueResponseDTO> values;
}
