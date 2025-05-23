package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.response.ProductPoolGenderResponseDTO;
import com.marketplaces.core.entity.ProductPoolGender;
import org.springframework.stereotype.Service;

@Service
public class ProductPoolGenderMapper {

    public ProductPoolGenderResponseDTO convert(ProductPoolGender productPoolGender) {
        if (productPoolGender == null) {
            return null;
        }
        ProductPoolGenderResponseDTO productPoolGenderResponseDTO = new ProductPoolGenderResponseDTO();
        productPoolGenderResponseDTO.setId(productPoolGender.getId());
        productPoolGenderResponseDTO.setName(productPoolGender.getName());
        return productPoolGenderResponseDTO;
    }
}
