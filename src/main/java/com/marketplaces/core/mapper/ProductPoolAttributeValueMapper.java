package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.create.ProductPoolAttributeValueCreateRequestDTO;
import com.marketplaces.core.dto.response.ProductPoolAttributeValueResponseDTO;
import com.marketplaces.core.entity.ProductPoolAttributeValue;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ProductPoolAttributeValueMapper {

    private final ProductPoolAttributeMapper attributeMapper;

    public ProductPoolAttributeValueMapper(@Lazy ProductPoolAttributeMapper attributeMapper){
        this.attributeMapper = attributeMapper;
    }

    public ProductPoolAttributeValue convert(ProductPoolAttributeValueCreateRequestDTO createDTO) {
        ProductPoolAttributeValue productPoolAttributeValue = new ProductPoolAttributeValue();
        productPoolAttributeValue.setName(createDTO.getName());
        return productPoolAttributeValue;
    }

    public ProductPoolAttributeValueResponseDTO convert(ProductPoolAttributeValue attributeValue) {
        ProductPoolAttributeValueResponseDTO valueResponseDTO = convertWithEmptyAttribute(attributeValue);
        valueResponseDTO.setAttribute(attributeMapper.convertWithEmptyValues(attributeValue.getAttribute()));
        return valueResponseDTO;
    }

    public ProductPoolAttributeValueResponseDTO convertWithEmptyAttribute(ProductPoolAttributeValue attributeValue) {
        ProductPoolAttributeValueResponseDTO valueResponseDTO = new ProductPoolAttributeValueResponseDTO();
        valueResponseDTO.setId(attributeValue.getId());
        valueResponseDTO.setName(attributeValue.getName());
        return valueResponseDTO;
    }

}
