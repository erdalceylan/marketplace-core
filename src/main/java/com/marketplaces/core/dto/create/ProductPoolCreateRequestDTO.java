package com.marketplaces.core.dto.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class ProductPoolCreateRequestDTO {
    @NotNull
    @Size(min = 16, max = 512)
    private String title;
    private String modelCode;
    private String description;
    @NotNull
    private Integer categoryId;
    private Short colorId;
    private Byte genderId;
    @NotNull
    private Integer brandId;
    private Set<Long> attributeValueIds;
}
