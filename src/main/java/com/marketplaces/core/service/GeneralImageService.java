package com.marketplaces.core.service;

import com.marketplaces.core.entity.GeneralImage;
import com.marketplaces.core.repository.GeneralImageRepository;
import org.springframework.stereotype.Service;

@Service
public class GeneralImageService {

    private final GeneralImageRepository generalImageRepository;
    public GeneralImageService(GeneralImageRepository generalImageRepository) {
        this.generalImageRepository = generalImageRepository;
    }

    public GeneralImage save(GeneralImage generalImage) {
        return generalImageRepository.save(generalImage);
    }

    public GeneralImage get(Long id) {
        return generalImageRepository.findById(id).orElse(null);
    }
}
