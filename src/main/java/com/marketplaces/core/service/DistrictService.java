package com.marketplaces.core.service;

import com.marketplaces.core.entity.District;
import com.marketplaces.core.repository.DistrictRepository;
import org.springframework.stereotype.Service;

@Service
public class DistrictService {

    private final DistrictRepository districtRepository;
    public DistrictService(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    public District save(District district) {
        return districtRepository.save(district);
    }

    public District get(Integer id) {
        return districtRepository.findById(id).orElse(null);
    }
}
