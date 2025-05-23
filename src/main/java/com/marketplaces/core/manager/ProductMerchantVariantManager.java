package com.marketplaces.core.manager;

import com.marketplaces.core.dto.create.ProductMerchantVariantCreateRequestDTO;
import com.marketplaces.core.dto.response.ProductMerchantVariantResponseDTO;
import com.marketplaces.core.entity.ProductMerchantVariant;
import com.marketplaces.core.mapper.ProductMerchantVariantMapper;
import com.marketplaces.core.service.MerchantService;
import com.marketplaces.core.service.ProductMerchantVariantService;
import com.marketplaces.core.service.ProductPoolVariantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductMerchantVariantManager {

    private final ProductMerchantVariantService productMerchantVariantService;
    private final ProductMerchantVariantMapper productMerchantVariantMapper;
    private final MerchantService merchantService;
    private final ProductPoolVariantService productPoolVariantService;

    public ProductMerchantVariantManager(
            ProductMerchantVariantService productMerchantVariantService,
            ProductMerchantVariantMapper productMerchantVariantMapper,
            MerchantService merchantService,
            ProductPoolVariantService productPoolVariantService
    ) {
        this.productMerchantVariantService = productMerchantVariantService;
        this.productMerchantVariantMapper = productMerchantVariantMapper;
        this.merchantService = merchantService;
        this.productPoolVariantService = productPoolVariantService;
    }

    @Transactional
    public ProductMerchantVariantResponseDTO create(ProductMerchantVariantCreateRequestDTO requestDTO) {
        ProductMerchantVariant productMerchantVariant = productMerchantVariantMapper.convert(requestDTO);
        productMerchantVariant.setMerchant(merchantService.findById(requestDTO.getMerchantId()));
        productMerchantVariant.setProductPoolVariant(productPoolVariantService.findById(requestDTO.getProductPoolVariantId()));
        productMerchantVariantService.save(productMerchantVariant);
        return productMerchantVariantMapper.convert(productMerchantVariant);
    }
}
