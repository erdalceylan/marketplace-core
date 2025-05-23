package com.marketplaces.core.manager;

import com.marketplaces.core.dto.create.ProductPoolVariantCreateRequestDTO;
import com.marketplaces.core.dto.response.ProductPoolVariantResponseDTO;
import com.marketplaces.core.entity.ProductPoolVariant;
import com.marketplaces.core.mapper.ProductPoolVariantMapper;
import com.marketplaces.core.service.ProductPoolService;
import com.marketplaces.core.service.ProductPoolVariantOptionValueService;
import com.marketplaces.core.service.ProductPoolVariantService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ProductPoolVariantManager {

    private final ProductPoolService productPoolService;
    private final ProductPoolVariantService productPoolVariantService;
    private final ProductPoolVariantOptionValueService productPoolVariantOptionValueService;
    private final ProductPoolVariantMapper productPoolVariantMapper;

    public ProductPoolVariantManager(
            ProductPoolService productPoolService,
            ProductPoolVariantService productPoolVariantService,
            ProductPoolVariantOptionValueService productPoolVariantOptionValueService,
            @Lazy ProductPoolVariantMapper productPoolVariantMapper
            ){
        this.productPoolService = productPoolService;
        this.productPoolVariantService = productPoolVariantService;
        this.productPoolVariantOptionValueService = productPoolVariantOptionValueService;
        this.productPoolVariantMapper = productPoolVariantMapper;
    }
    public ProductPoolVariantResponseDTO create(ProductPoolVariantCreateRequestDTO requestDTO) {
        ProductPoolVariant productPoolVariant = productPoolVariantMapper.convert(requestDTO);
        productPoolVariant.setProductPool(productPoolService.findById(requestDTO.getProductPoolId()));
        if (requestDTO.getOptionValueId() != null) {
            productPoolVariant.setOptionValue(productPoolVariantOptionValueService.findById(requestDTO.getOptionValueId()));
        }
        productPoolVariantService.save(productPoolVariant);
        return productPoolVariantMapper.convert(productPoolVariant);
    }
}
