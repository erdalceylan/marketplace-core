package com.marketplaces.core.controller;

import com.marketplaces.core.dto.create.AddressCreateRequestDTO;
import com.marketplaces.core.dto.response.AddressResponseDTO;
import com.marketplaces.core.manager.AddressManager;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressManager addressManager;

    public AddressController(AddressManager addressManager) {
        this.addressManager = addressManager;
    }

    @PostMapping
    public ResponseEntity<AddressResponseDTO> create(@Valid @RequestBody AddressCreateRequestDTO addressCreateRequestDTO) {
        return ResponseEntity.ok(addressManager.create(addressCreateRequestDTO));
    }
}
