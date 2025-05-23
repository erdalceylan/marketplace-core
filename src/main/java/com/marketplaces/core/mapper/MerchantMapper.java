package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.response.MerchantResponseDTO;
import com.marketplaces.core.entity.Merchant;
import org.springframework.stereotype.Service;

@Service
public class MerchantMapper {

    private final GeneralImageMapper generalImageMapper;

    public MerchantMapper(
            GeneralImageMapper generalImageMapper
    ) {
        this.generalImageMapper = generalImageMapper;
    }

    public MerchantResponseDTO convert(Merchant merchant) {
        MerchantResponseDTO responseDTO = new MerchantResponseDTO();
        responseDTO.setId(merchant.getId());
        responseDTO.setName(merchant.getName());
        responseDTO.setOfficialName(merchant.getOfficialName());
        responseDTO.setRate(merchant.getRate());
        if (merchant.getLogoImage() != null) {
            responseDTO.setLogoImage(generalImageMapper.convert(merchant.getLogoImage()));
        }
        return responseDTO;
    }
}
