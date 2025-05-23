package com.marketplaces.core.controller;

import com.marketplaces.core.dto.response.BrandResponseDTO;
import com.marketplaces.core.manager.BrandManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {


    private final BrandManager brandManager;

    BrandController(BrandManager brandManager) {
        this.brandManager = brandManager;
    }

    @GetMapping("/top")
    public ResponseEntity<List<BrandResponseDTO>> list() {
        return ResponseEntity.ok(this.brandManager.getTopList());
    }
}
