package com.marketplaces.core.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductPoolVariantCreateRequestDTO {
    @NotNull
    private Long productPoolId;
    @NotNull
    private String barcode;
    private Long optionValueId;
}
