package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.response.ProductPoolVariantOptionValueResponseDTO;
import com.marketplaces.core.entity.ProductPoolVariantOptionValue;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ProductPoolVariantOptionValueMapper {

    private final ProductPoolVariantOptionMapper optionMapper;

    public ProductPoolVariantOptionValueMapper(@Lazy ProductPoolVariantOptionMapper optionMapper){
        this.optionMapper = optionMapper;
    }

    public ProductPoolVariantOptionValueResponseDTO convert(ProductPoolVariantOptionValue optionValue) {
        if(optionValue == null){
            return null;
        }
        ProductPoolVariantOptionValueResponseDTO valueResponseDTO = convertWithEmptyAttribute(optionValue);
        valueResponseDTO.setOption(optionMapper.convertWithEmptyValues(optionValue.getOption()));
        return valueResponseDTO;
    }

    public ProductPoolVariantOptionValueResponseDTO convertWithEmptyAttribute(ProductPoolVariantOptionValue optionValue) {
        if(optionValue == null){
            return null;
        }
        ProductPoolVariantOptionValueResponseDTO valueResponseDTO  = new ProductPoolVariantOptionValueResponseDTO();
        valueResponseDTO.setId(optionValue.getId());
        valueResponseDTO.setName(optionValue.getName());
        return valueResponseDTO;
    }

}
