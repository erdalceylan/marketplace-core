package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.response.DistrictResponseDTO;
import com.marketplaces.core.entity.District;
import com.marketplaces.core.repository.DistrictRepository;
import org.springframework.stereotype.Service;

@Service
public class DistrictMapper {

    private final CityMapper cityMapper;

    public DistrictMapper(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    public DistrictResponseDTO convert(District district) {
        DistrictResponseDTO districtResponseDTO = convertWithoutCity(district);
        if(district.getCity() != null) {
            districtResponseDTO.setCity(cityMapper.convert(district.getCity()));
        }

        return districtResponseDTO;
    }

    public DistrictResponseDTO convertWithoutCity(District district) {
        DistrictResponseDTO districtResponseDTO = new DistrictResponseDTO();
        districtResponseDTO.setId(district.getId());
        districtResponseDTO.setName(district.getName());
        return districtResponseDTO;
    }
}
