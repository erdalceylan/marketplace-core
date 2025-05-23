package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.create.ProductPoolColorCreateRequestDTO;
import com.marketplaces.core.dto.response.ProductPoolColorResponseDTO;
import com.marketplaces.core.entity.ProductPoolColor;
import org.springframework.stereotype.Service;

@Service
public class ProductPoolColorMapper {
    public ProductPoolColor convert(ProductPoolColorCreateRequestDTO dto) {
        ProductPoolColor productPoolColor = new ProductPoolColor();
        productPoolColor.setName(dto.getName());
        productPoolColor.setCode(dto.getCode());
        return productPoolColor;
    }

    public ProductPoolColorResponseDTO convert(ProductPoolColor productPoolColor) {
        if (productPoolColor == null) {
            return null;
        }
        ProductPoolColorResponseDTO productPoolColorResponseDTO = new ProductPoolColorResponseDTO();
        productPoolColorResponseDTO.setId(productPoolColor.getId());
        productPoolColorResponseDTO.setName(productPoolColor.getName());
        productPoolColorResponseDTO.setCode(productPoolColor.getCode());
        return productPoolColorResponseDTO;
    }
}
