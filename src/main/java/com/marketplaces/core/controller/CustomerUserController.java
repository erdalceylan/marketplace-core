package com.marketplaces.core.controller;

import com.marketplaces.core.dto.create.CustomerUserCreateRequestDTO;
import com.marketplaces.core.dto.response.CustomerUserResponseDTO;
import com.marketplaces.core.manager.CustomerUserManager;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerUserController {

    private final CustomerUserManager customerUserManager;

    public CustomerUserController(CustomerUserManager customerUserManager) {
        this.customerUserManager = customerUserManager;
    }

    @PostMapping
    public ResponseEntity<CustomerUserResponseDTO> create(@Valid @RequestBody CustomerUserCreateRequestDTO customerUserRequestDTO) {
        return ResponseEntity.ok(customerUserManager.create(customerUserRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerUserResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(customerUserManager.get(id));
    }
}
