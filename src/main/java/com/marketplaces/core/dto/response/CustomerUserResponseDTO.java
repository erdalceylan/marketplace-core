package com.marketplaces.core.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CustomerUserResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private OffsetDateTime lastLoginAt;
}
