package com.marketplaces.core.dto.response;

import lombok.Data;

@Data
public class CategoryResponseDTO {
    private Integer id;
    private String name;
    private String fullName;
    private CategoryResponseDTO parent;
    private Integer parentId;
}
