package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.response.GeneralImageResponseDTO;
import com.marketplaces.core.entity.GeneralImage;
import org.springframework.stereotype.Service;

@Service
public class GeneralImageMapper {
    public GeneralImageResponseDTO convert(GeneralImage generalImage) {
        GeneralImageResponseDTO responseDTO = new GeneralImageResponseDTO();
        responseDTO.setId(generalImage.getId());
        responseDTO.setRoot(generalImage.getRoot());
        responseDTO.setFolder(generalImage.getFolder());
        responseDTO.setFileName(generalImage.getFileName());
        responseDTO.setLargeFileName(generalImage.getLargeFileName());
        return responseDTO;
    }

}
