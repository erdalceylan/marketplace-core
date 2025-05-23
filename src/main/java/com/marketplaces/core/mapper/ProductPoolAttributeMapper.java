package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.create.ProductPoolAttributeCreateRequestDTO;
import com.marketplaces.core.dto.response.ProductPoolAttributeResponseDTO;
import com.marketplaces.core.entity.ProductPoolAttribute;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ProductPoolAttributeMapper {

    private final ProductPoolAttributeValueMapper valueMapper;

    public ProductPoolAttributeMapper(@Lazy ProductPoolAttributeValueMapper valueMapper){
        this.valueMapper = valueMapper;
    }

    public ProductPoolAttribute convert(ProductPoolAttributeCreateRequestDTO createRequestDTO) {
        ProductPoolAttribute productPoolAttribute = new ProductPoolAttribute();
        productPoolAttribute.setName(createRequestDTO.getName());
        return productPoolAttribute;
    }

    public ProductPoolAttributeResponseDTO convert(ProductPoolAttribute attribute) {
        ProductPoolAttributeResponseDTO attributeResponseDTO = convertWithEmptyValues(attribute);
        attributeResponseDTO.setValues(attribute.getValues().stream().map(valueMapper::convertWithEmptyAttribute).toList());
        return attributeResponseDTO;
    }

    public ProductPoolAttributeResponseDTO convertWithEmptyValues(ProductPoolAttribute attribute) {
        ProductPoolAttributeResponseDTO attributeResponseDTO = new ProductPoolAttributeResponseDTO();
        attributeResponseDTO.setId(attribute.getId());
        attributeResponseDTO.setName(attribute.getName());
        return attributeResponseDTO;
    }

}
