package com.marketplaces.core.manager;

import com.marketplaces.core.dto.create.ProductPoolCreateRequestDTO;
import com.marketplaces.core.dto.response.ProductPoolResponseDTO;
import com.marketplaces.core.entity.ProductPool;
import com.marketplaces.core.entity.ProductPoolState;
import com.marketplaces.core.helper.UrlHelper;
import com.marketplaces.core.mapper.*;
import com.marketplaces.core.service.*;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductPoolManager {

    private final ProductPoolService productPoolService;
    private final ProductPoolMapper productPoolMapper;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ProductPoolColorService productPoolColorService;
    private final ProductPoolStateService productPoolStateService;
    private final ProductPoolAttributeValueService productPoolAttributeValueService;

    public ProductPoolManager(
            ProductPoolService productPoolService,
            CategoryService categoryService,
            BrandService brandService,
            ProductPoolColorService productPoolColorService,
            ProductPoolStateService productPoolStateService,
            ProductPoolMapper productPoolMapper,
            ProductPoolAttributeValueService productPoolAttributeValueService
            ) {
        this.productPoolService = productPoolService;
        this.productPoolMapper = productPoolMapper;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.productPoolColorService = productPoolColorService;
        this.productPoolStateService = productPoolStateService;
        this.productPoolAttributeValueService = productPoolAttributeValueService;
    }

    public ProductPoolResponseDTO create(ProductPoolCreateRequestDTO productPoolCreateRequestDTO) {
        ProductPool productPool = productPoolMapper.convert(productPoolCreateRequestDTO);
        productPool.setSlug("");
        productPool.setCategory(this.categoryService.findById(productPoolCreateRequestDTO.getCategoryId()));
        productPool.setBrand(brandService.findById(productPoolCreateRequestDTO.getBrandId()));
        productPool.setColor(productPoolColorService.findById(productPoolCreateRequestDTO.getColorId()));
        productPool.setState(this.productPoolStateService.findById(ProductPoolState.State.ACTIVE.getValue()));
        productPool.setAttributeValues(productPoolAttributeValueService.findByIds(productPoolCreateRequestDTO.getAttributeValueIds()));
        this.productPoolService.save(productPool);
        productPool.setSlug(UrlHelper.toSlug(productPool.getTitle(), productPool.getId().toString()));
        this.productPoolService.save(productPool);

        return productPoolMapper.convert(productPool);
    }

    @Transactional
    public ProductPoolResponseDTO convertRevertFull(Long id) {
        ProductPool productPool = productPoolService.findById(id);
        return productPoolMapper.convertReverseDeep(productPool);
    }

    @Transactional
    public ProductPoolResponseDTO convertRevertFull(ProductPool productPool) {
        return productPoolMapper.convertReverseDeep(productPool);
    }

}
