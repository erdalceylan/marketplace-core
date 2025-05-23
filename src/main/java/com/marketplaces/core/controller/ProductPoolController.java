package com.marketplaces.core.controller;

import com.marketplaces.core.dto.create.ProductPoolCreateRequestDTO;
import com.marketplaces.core.dto.response.ProductPoolResponseDTO;
import com.marketplaces.core.manager.ProductPoolManager;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-pools")
public class ProductPoolController {


    private final ProductPoolManager productPoolManager;

    public ProductPoolController(
            ProductPoolManager productPoolManager
    ) {
        this.productPoolManager = productPoolManager;
    }

    @PostMapping
    public ResponseEntity<ProductPoolResponseDTO> create(@Valid @RequestBody ProductPoolCreateRequestDTO productPoolRequestDTO) {
        return ResponseEntity.ok(productPoolManager.create(productPoolRequestDTO));
    }

    @GetMapping("/full/{id}")
    public ResponseEntity<ProductPoolResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(productPoolManager.convertRevertFull(id));
    }


}
