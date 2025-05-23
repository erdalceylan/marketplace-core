package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.create.ProductPoolVariantCreateRequestDTO;
import com.marketplaces.core.dto.response.ProductPoolVariantResponseDTO;
import com.marketplaces.core.entity.ProductPoolVariant;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class ProductPoolVariantMapper {

    private final ProductPoolMapper productPoolMapper;
    private final ProductMerchantVariantMapper productMerchantVariantMapper;
    private final ProductPoolVariantOptionValueMapper productPoolVariantOptionValueMapper;

    public ProductPoolVariantMapper(
            @Lazy ProductPoolMapper productPoolMapper,
            @Lazy ProductMerchantVariantMapper productMerchantVariantMapper,
            ProductPoolVariantOptionValueMapper productPoolVariantOptionValueMapper
    ) {
        this.productPoolMapper = productPoolMapper;
        this.productMerchantVariantMapper = productMerchantVariantMapper;
        this.productPoolVariantOptionValueMapper = productPoolVariantOptionValueMapper;
    }

    public ProductPoolVariant convert(ProductPoolVariantCreateRequestDTO requestDTO) {
        ProductPoolVariant productPoolVariant = new ProductPoolVariant();
        productPoolVariant.setBarcode(requestDTO.getBarcode());
        productPoolVariant.setCreatedAt(OffsetDateTime.now());
        return productPoolVariant;
    }

    public ProductPoolVariantResponseDTO convert(ProductPoolVariant productPoolVariant) {
        ProductPoolVariantResponseDTO responseDTO = convertWithoutProduct(productPoolVariant);
        responseDTO.setProductPool(productPoolMapper.convert(productPoolVariant.getProductPool()));
        return responseDTO;
    }

    public ProductPoolVariantResponseDTO convertWithoutProduct(ProductPoolVariant productPoolVariant) {
        ProductPoolVariantResponseDTO responseDTO = new ProductPoolVariantResponseDTO();
        responseDTO.setId(productPoolVariant.getId());
        responseDTO.setBarcode(productPoolVariant.getBarcode());

        responseDTO.setOptionValue(productPoolVariantOptionValueMapper.convert(productPoolVariant.getOptionValue()));
        return responseDTO;
    }

    public ProductPoolVariantResponseDTO convertReverseDeep(ProductPoolVariant productPoolVariant) {
        ProductPoolVariantResponseDTO responseDTO = convertWithoutProduct(productPoolVariant);
        responseDTO.setMerchantVariants(
                productPoolVariant.getMerchantVariants()
                        .stream().map(productMerchantVariantMapper::convertWithoutProductPoolVariant).toList());
        return responseDTO;

    }
}
