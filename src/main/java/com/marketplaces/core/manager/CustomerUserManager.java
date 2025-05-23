package com.marketplaces.core.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplaces.core.service.CustomerUserService;
import com.marketplaces.core.dto.create.CustomerUserCreateRequestDTO;
import com.marketplaces.core.dto.response.CustomerUserResponseDTO;
import com.marketplaces.core.entity.CustomerUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class CustomerUserManager {

    private final ObjectMapper mapper;
    private final CustomerUserService customerUserService;
    private final PasswordEncoder passwordEncoder;

    public CustomerUserManager(
            ObjectMapper objectMapper,
            CustomerUserService customerUserService,
            PasswordEncoder passwordEncoder
    ) {
        this.mapper = objectMapper;
        this.customerUserService = customerUserService;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerUserResponseDTO create(CustomerUserCreateRequestDTO customerUserRequestDTO) {
        CustomerUser customerUser = this.mapper.convertValue(customerUserRequestDTO, CustomerUser.class);
        customerUser.setCreatedAt(OffsetDateTime.now());
        customerUser.setUpdatedAt(OffsetDateTime.now());
        customerUser.setPassword(passwordEncoder.encode(customerUserRequestDTO.getPassword()));
        customerUser = this.customerUserService.save(customerUser);

        return this.mapper.convertValue(customerUser, CustomerUserResponseDTO.class);
    }

    public CustomerUserResponseDTO get(Long id) {
        CustomerUser customerUser = this.customerUserService.findById(id);
        return this.mapper.convertValue(customerUser, CustomerUserResponseDTO.class);
    }
}
