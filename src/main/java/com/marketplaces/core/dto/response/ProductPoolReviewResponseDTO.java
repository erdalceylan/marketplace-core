package com.marketplaces.core.dto.response;

import lombok.Data;

@Data
public class ProductPoolReviewResponseDTO {
    Long id;
    CustomerUserResponseDTO customerUser;
    String merchantId;
    String merchantName;
    String productPoolId;
    String text;
    Byte rate;
}
