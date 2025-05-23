package com.marketplaces.core.dto.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductMerchantVariantCreateRequestDTO {
    @NotNull
    private Long productPoolVariantId;
    @NotNull
    private Long merchantId;
    @Min(1)
    @Max(99999)
    @NotNull
    private BigDecimal originalPrice;
    @Min(1)
    @Max(99999)
    @NotNull
    private BigDecimal discountPrice;
    @Min(0)
    @NotNull
    private Integer stockQuantity;
}
