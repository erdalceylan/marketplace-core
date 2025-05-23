package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.response.ProductPoolImageResponseDTO;
import com.marketplaces.core.entity.ProductPoolImage;
import org.springframework.stereotype.Service;

@Service
public class ProductPoolImageMapper {

    public ProductPoolImageResponseDTO convert(ProductPoolImage productPoolImage) {
        return convertWithoutProduct(productPoolImage);
    }


    public ProductPoolImageResponseDTO convertWithoutProduct(ProductPoolImage productPoolImage) {
        ProductPoolImageResponseDTO productPoolImageResponseDTO = new ProductPoolImageResponseDTO();
        productPoolImageResponseDTO.setId(productPoolImage.getId());
        productPoolImageResponseDTO.setRoot(productPoolImage.getRoot());
        productPoolImageResponseDTO.setFolder(productPoolImage.getFolder());
        productPoolImageResponseDTO.setFileName(productPoolImage.getFileName());
        productPoolImageResponseDTO.setLargeFileName(productPoolImage.getLargeFileName());

        return productPoolImageResponseDTO;
    }
}
