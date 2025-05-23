package com.marketplaces.core.controller;

import com.marketplaces.core.dto.create.ProductMerchantVariantCreateRequestDTO;
import com.marketplaces.core.dto.response.ProductMerchantVariantResponseDTO;
import com.marketplaces.core.manager.ProductMerchantVariantManager;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product-merchant-variants")
public class ProductMerchantVariantController {

    private final ProductMerchantVariantManager productMerchantVariantManager;

    public ProductMerchantVariantController(
            ProductMerchantVariantManager productMerchantVariantManager
    ){
        this.productMerchantVariantManager = productMerchantVariantManager;
    }

    @PostMapping
    public ResponseEntity<ProductMerchantVariantResponseDTO> create(@Valid @RequestBody ProductMerchantVariantCreateRequestDTO requestDTO) {
        return ResponseEntity.ok(productMerchantVariantManager.create(requestDTO));
    }
}
