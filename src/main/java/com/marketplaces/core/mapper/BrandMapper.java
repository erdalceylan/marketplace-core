package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.create.BrandCreateRequestDTO;
import com.marketplaces.core.dto.response.BrandResponseDTO;
import com.marketplaces.core.entity.Brand;
import org.springframework.stereotype.Service;


@Service
public class BrandMapper {

    public Brand convert(BrandCreateRequestDTO dto) {
        Brand brand = new Brand();
        brand.setId(dto.getId());
        brand.setName(dto.getName());
        brand.setFullName(dto.getFullName());
        return brand;
    }

    public BrandResponseDTO convert(Brand brand) {
        BrandResponseDTO brandResponseDTO = new BrandResponseDTO();
        brandResponseDTO.setId(brand.getId());
        brandResponseDTO.setName(brand.getName());
        brandResponseDTO.setFullName(brand.getFullName());
        return brandResponseDTO;
    }
}
