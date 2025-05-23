package com.marketplaces.core.controller;

import com.marketplaces.core.dto.response.MerchantResponseDTO;
import com.marketplaces.core.manager.MerchantManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/merchants")
public class MerchantController {

    private final MerchantManager merchantManager;

    MerchantController(MerchantManager merchantManager) {
        this.merchantManager = merchantManager;
    }

    @GetMapping("/top")
    public ResponseEntity<List<MerchantResponseDTO>> list() {
        return ResponseEntity.ok(this.merchantManager.getTopList());
    }
}
