package com.marketplaces.core.controller;

import com.marketplaces.core.dto.create.ProductPoolVariantCreateRequestDTO;
import com.marketplaces.core.dto.response.ProductPoolVariantResponseDTO;
import com.marketplaces.core.manager.ProductPoolVariantManager;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product-pool-variants")
public class ProductPoolVariantController {


    private final ProductPoolVariantManager productPoolVariantManager;

    public ProductPoolVariantController(
            ProductPoolVariantManager productPoolVariantManager
    ) {
        this.productPoolVariantManager = productPoolVariantManager;
    }

    @PostMapping
    public ResponseEntity<ProductPoolVariantResponseDTO> create(@Valid @RequestBody ProductPoolVariantCreateRequestDTO requestDTO) {
        return ResponseEntity.ok(productPoolVariantManager.create(requestDTO));
    }
}
