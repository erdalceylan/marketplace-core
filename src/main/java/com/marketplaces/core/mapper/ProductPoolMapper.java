package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.create.ProductPoolCreateRequestDTO;
import com.marketplaces.core.dto.response.ProductPoolResponseDTO;
import com.marketplaces.core.entity.ProductPool;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class ProductPoolMapper {

    private final CategoryMapper categoryMapper;
    private final BrandMapper brandMapper;
    private final ProductPoolColorMapper productPoolColorMapper;
    private final ProductPoolStateMapper productPoolStateMapper;
    private final ProductPoolGenderMapper productPoolGenderMapper;
    private final ProductPoolVariantMapper productPoolVariantMapper;
    private final ProductPoolImageMapper productPoolImageMapper;
    private final ProductPoolAttributeValueMapper productPoolAttributeValueMapper;

    public ProductPoolMapper(
            CategoryMapper categoryMapper,
            BrandMapper brandMapper,
            ProductPoolColorMapper productPoolColorMapper,
            ProductPoolStateMapper productPoolStateMapper,
            ProductPoolGenderMapper productPoolGenderMapper,
            ProductPoolAttributeValueMapper productPoolAttributeValueMapper,
            @Lazy ProductPoolVariantMapper productPoolVariantMapper,
            @Lazy ProductPoolImageMapper productPoolImageMapper

    ) {
        this.categoryMapper = categoryMapper;
        this.brandMapper = brandMapper;
        this.productPoolColorMapper = productPoolColorMapper;
        this.productPoolStateMapper = productPoolStateMapper;
        this.productPoolGenderMapper = productPoolGenderMapper;
        this.productPoolVariantMapper = productPoolVariantMapper;
        this.productPoolImageMapper = productPoolImageMapper;
        this.productPoolAttributeValueMapper = productPoolAttributeValueMapper;
    }

    public ProductPool convert(ProductPoolCreateRequestDTO dto) {
        ProductPool productPool = new ProductPool();
        productPool.setTitle(dto.getTitle());
        productPool.setModelCode(dto.getModelCode());
        productPool.setDescription(dto.getDescription());
        productPool.setCreatedAt(OffsetDateTime.now());
        return productPool;
    }

    public ProductPoolResponseDTO convert(ProductPool productPool) {
        ProductPoolResponseDTO productPoolResponseDTO = new ProductPoolResponseDTO();
        productPoolResponseDTO.setId(productPool.getId());
        productPoolResponseDTO.setTitle(productPool.getTitle());
        productPoolResponseDTO.setModelCode(productPool.getModelCode());
        productPoolResponseDTO.setRate(productPool.getRate());
        productPoolResponseDTO.setDescription(productPool.getDescription());
        productPoolResponseDTO.setSlug(productPool.getSlug());
        productPoolResponseDTO.setBrand(brandMapper.convert(productPool.getBrand()));
        productPoolResponseDTO.setCategory(categoryMapper.convert(productPool.getCategory()));
        productPoolResponseDTO.setCategories(categoryMapper.convertToList(productPool.getCategory()));
        productPoolResponseDTO.setColor(productPoolColorMapper.convert(productPool.getColor()));
        productPoolResponseDTO.setState(productPoolStateMapper.convert(productPool.getState()));
        productPoolResponseDTO.setGender(productPoolGenderMapper.convert(productPool.getGender()));
        productPoolResponseDTO.setAttributeValues(productPool.getAttributeValues().stream().map(productPoolAttributeValueMapper::convert).toList());
        return productPoolResponseDTO;
    }


    public ProductPoolResponseDTO convertReverseDeep(ProductPool productPool) {
        ProductPoolResponseDTO productPoolResponseDTO = convert(productPool);
        productPoolResponseDTO.setVariants(productPool.getVariants().stream().map(productPoolVariantMapper::convertReverseDeep).toList());
        productPoolResponseDTO.setImages(productPool.getImages().stream().map(productPoolImageMapper::convertWithoutProduct).toList());
        return productPoolResponseDTO;

    }

}
