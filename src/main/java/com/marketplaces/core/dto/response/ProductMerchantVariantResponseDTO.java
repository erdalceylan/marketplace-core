package com.marketplaces.core.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductMerchantVariantResponseDTO {
    private Long id;
    private MerchantResponseDTO merchant;
    private ProductPoolVariantResponseDTO productPoolVariant;
    private BigDecimal originalPrice;
    private BigDecimal discountedPrice;
    private Integer stockQuantity;
}
