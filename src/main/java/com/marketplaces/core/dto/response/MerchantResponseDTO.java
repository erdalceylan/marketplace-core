package com.marketplaces.core.dto.response;

import lombok.Data;

@Data
public class MerchantResponseDTO {
    private Long id;
    private String name;
    private String officialName;
    private Float rate;
    private GeneralImageResponseDTO logoImage;
}
