package com.marketplaces.core.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplaces.core.dto.create.BrandCreateRequestDTO;
import com.marketplaces.core.dto.response.BrandResponseDTO;
import com.marketplaces.core.entity.Brand;
import com.marketplaces.core.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandManager {

    private final BrandService brandService;
    private final ObjectMapper mapper;

    public BrandManager(
            BrandService brandService,
            ObjectMapper mapper
    ) {
        this.brandService = brandService;
        this.mapper = mapper;
    }

    public BrandResponseDTO create(BrandCreateRequestDTO brandCreateRequestDTO) {
        Brand brand = new Brand();
        brand.setName(brandCreateRequestDTO.getName());
        brand.setFullName(brandCreateRequestDTO.getFullName());

        brandService.save(brand);

        return mapper.convertValue(brand, BrandResponseDTO.class);
    }

    public List<BrandResponseDTO> getTopList() {
        List<Brand> brands =  this.brandService.getTopList(100, 0);
        return brands.stream().map((brand) ->mapper.convertValue(brand, BrandResponseDTO.class)).toList();
    }
}
