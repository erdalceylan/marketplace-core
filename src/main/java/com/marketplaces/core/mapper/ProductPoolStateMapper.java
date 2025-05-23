package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.response.ProductPoolStateResponseDTO;
import com.marketplaces.core.entity.ProductPoolState;
import org.springframework.stereotype.Service;

@Service
public class ProductPoolStateMapper {
    public ProductPoolStateResponseDTO convert(ProductPoolState productPoolState) {
        if (productPoolState == null) {
            return null;
        }
        ProductPoolStateResponseDTO productPoolStateResponseDTO = new ProductPoolStateResponseDTO();
        productPoolStateResponseDTO.setId(productPoolState.getId());
        productPoolStateResponseDTO.setName(productPoolState.getName());
        return productPoolStateResponseDTO;
    }
}
