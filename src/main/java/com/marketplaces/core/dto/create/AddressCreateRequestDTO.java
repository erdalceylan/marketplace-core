package com.marketplaces.core.dto.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressCreateRequestDTO {

    @NotNull
    @Size(min = 2, max = 256)
    private String title;

    @NotNull
    @Size(min = 16, max = 2048)
    private String text;

    @NotNull
    private Short countryId;

    @NotNull
    private Integer cityId;

    @NotNull
    private Integer districtId;
}
