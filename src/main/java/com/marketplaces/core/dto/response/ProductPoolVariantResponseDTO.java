package com.marketplaces.core.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductPoolVariantResponseDTO {
    private Long id;
    private String barcode;
    private ProductPoolResponseDTO productPool;
    private ProductPoolVariantOptionValueResponseDTO optionValue;
    private List<ProductMerchantVariantResponseDTO> merchantVariants;
}
