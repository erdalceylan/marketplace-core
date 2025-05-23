package com.marketplaces.core.dto.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductPoolColorCreateRequestDTO {
    @Size(min = 2, max = 255)
    @NotNull
    private String name;
    private String code;
}
