package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.create.ProductMerchantVariantCreateRequestDTO;
import com.marketplaces.core.dto.response.ProductMerchantVariantResponseDTO;
import com.marketplaces.core.entity.ProductMerchantVariant;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class ProductMerchantVariantMapper {

    private final MerchantMapper merchantMapper;
    private final ProductPoolVariantMapper productPoolVariantMapper;

    public ProductMerchantVariantMapper(
            MerchantMapper merchantMapper,
            @Lazy ProductPoolVariantMapper productPoolVariantMapper
    ) {
        this.merchantMapper = merchantMapper;
        this.productPoolVariantMapper = productPoolVariantMapper;
    }


    public ProductMerchantVariant convert(ProductMerchantVariantCreateRequestDTO createRequestDTO) {
        ProductMerchantVariant productMerchantVariant = new ProductMerchantVariant();
        productMerchantVariant.setDiscountedPrice(createRequestDTO.getDiscountPrice());
        productMerchantVariant.setOriginalPrice(createRequestDTO.getOriginalPrice());
        productMerchantVariant.setStockQuantity(createRequestDTO.getStockQuantity());
        productMerchantVariant.setCreatedAt(OffsetDateTime.now());

        return productMerchantVariant;
    }

    public ProductMerchantVariantResponseDTO convert(ProductMerchantVariant productMerchantVariant) {
        ProductMerchantVariantResponseDTO responseDTO = convertWithoutProductPoolVariant(productMerchantVariant);
        responseDTO.setProductPoolVariant(productPoolVariantMapper.convert(productMerchantVariant.getProductPoolVariant()));
        return responseDTO;
    }

    public ProductMerchantVariantResponseDTO convertWithoutProductPoolVariant(ProductMerchantVariant productMerchantVariant) {
        ProductMerchantVariantResponseDTO responseDTO =  new ProductMerchantVariantResponseDTO();
        responseDTO.setId(productMerchantVariant.getId());
        responseDTO.setOriginalPrice(productMerchantVariant.getOriginalPrice());
        responseDTO.setDiscountedPrice(productMerchantVariant.getDiscountedPrice());
        responseDTO.setStockQuantity(productMerchantVariant.getStockQuantity());
        responseDTO.setMerchant(merchantMapper.convert(productMerchantVariant.getMerchant()));

        return responseDTO;
    }
}
