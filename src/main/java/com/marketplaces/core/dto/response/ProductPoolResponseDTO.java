package com.marketplaces.core.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductPoolResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String slug;
    private  String modelCode;
    private Float rate;
    private BrandResponseDTO brand;
    private CategoryResponseDTO category;
    private List<CategoryResponseDTO> categories;
    private ProductPoolColorResponseDTO color;
    private ProductPoolStateResponseDTO state;
    private ProductPoolGenderResponseDTO gender;
    private List<ProductPoolVariantResponseDTO> variants;
    private List<ProductPoolImageResponseDTO> images;
    private List<ProductPoolAttributeValueResponseDTO> attributeValues;
}
