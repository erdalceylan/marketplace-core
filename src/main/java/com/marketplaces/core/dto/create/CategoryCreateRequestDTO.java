package com.marketplaces.core.dto.create;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryCreateRequestDTO {
    @Size(min = 2, max = 64)
    private String name;
    @Size(min = 2, max = 256)
    private String fullName;
    private Integer parentId;
}
