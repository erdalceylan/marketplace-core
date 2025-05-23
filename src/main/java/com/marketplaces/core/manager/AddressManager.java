package com.marketplaces.core.manager;

import com.marketplaces.core.dto.create.AddressCreateRequestDTO;
import com.marketplaces.core.dto.response.AddressResponseDTO;
import com.marketplaces.core.entity.Address;
import com.marketplaces.core.mapper.AddressMapper;
import com.marketplaces.core.repository.CityRepository;
import com.marketplaces.core.repository.CountryRepository;
import com.marketplaces.core.repository.DistrictRepository;
import com.marketplaces.core.service.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressManager {

    private final AddressService addressService;
    private final AddressMapper addressMapper;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;

    AddressManager(AddressService addressService,
                   AddressMapper addressMapper,
                   CountryRepository countryRepository,
                   CityRepository cityRepository,
                   DistrictRepository districtRepository) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
    }

    public AddressResponseDTO create(AddressCreateRequestDTO addressCreateRequestDTO) {
        Address address = addressMapper.convert(addressCreateRequestDTO);
        address.setCountry(countryRepository.findById(addressCreateRequestDTO.getCountryId()).orElse(null));
        address.setCity(cityRepository.findById(addressCreateRequestDTO.getCityId()).orElse(null));
        address.setDistrict(districtRepository.findById(addressCreateRequestDTO.getDistrictId()).orElse(null));
        addressService.save(address);
        return addressMapper.convert(address);
    }
}
