package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.response.ProductPoolVariantOptionResponseDTO;
import com.marketplaces.core.entity.ProductPoolVariantOption;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ProductPoolVariantOptionMapper {


    private final ProductPoolVariantOptionValueMapper valueMapper;

    public ProductPoolVariantOptionMapper(@Lazy ProductPoolVariantOptionValueMapper valueMapper){
        this.valueMapper = valueMapper;
    }

    public ProductPoolVariantOptionResponseDTO convert(ProductPoolVariantOption option) {
        ProductPoolVariantOptionResponseDTO attributeResponseDTO = convertWithEmptyValues(option);
        attributeResponseDTO.setValues(option.getValues().stream().map(valueMapper::convertWithEmptyAttribute).toList());
        return attributeResponseDTO;
    }

    public ProductPoolVariantOptionResponseDTO convertWithEmptyValues(ProductPoolVariantOption option) {
        ProductPoolVariantOptionResponseDTO optionResponseDTO = new ProductPoolVariantOptionResponseDTO();
        optionResponseDTO.setId(option.getId());
        optionResponseDTO.setName(option.getName());
        return optionResponseDTO;
    }

}
