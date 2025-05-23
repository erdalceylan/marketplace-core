package com.marketplaces.core.dto.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ProductPoolReviewCreateDTO {
    @NotNull
    private Long customerUserId;
    @NotNull
    private Long productPoolId;
    @NotNull
    private Long merchantId;
    @NotNull
    @Size(min = 5)
    private String text;
    @NotNull
    @Min(1)
    @Max(5)
    private Byte rate;

    private OffsetDateTime createdAt;
}
