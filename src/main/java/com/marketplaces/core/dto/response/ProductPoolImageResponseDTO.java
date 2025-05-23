package com.marketplaces.core.dto.response;

import lombok.Data;

@Data
public class ProductPoolImageResponseDTO {
    private Long id;
    private String root;
    private String folder;
    private String fileName;
    private String largeFileName;
    private ProductPoolResponseDTO productPool;
}
