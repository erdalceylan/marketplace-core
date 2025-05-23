package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.create.AddressCreateRequestDTO;
import com.marketplaces.core.dto.response.AddressResponseDTO;
import com.marketplaces.core.entity.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressMapper {

    private final CountryMapper countryMapper;
    private final CityMapper cityMapper;
    private final DistrictMapper districtMapper;

    public AddressMapper(CountryMapper countryMapper, CityMapper cityMapper, DistrictMapper districtMapper) {
        this.countryMapper = countryMapper;
        this.cityMapper = cityMapper;
        this.districtMapper = districtMapper;
    }

    public Address convert(AddressCreateRequestDTO addressCreateRequestDTO) {
        Address address = new Address();
        address.setTitle(addressCreateRequestDTO.getTitle());
        address.setText(addressCreateRequestDTO.getText());
        return address;
    }

    public AddressResponseDTO convert(Address address) {
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
        addressResponseDTO.setId(address.getId());
        addressResponseDTO.setTitle(address.getTitle());
        addressResponseDTO.setText(address.getText());

        if (address.getCountry() != null) {
            addressResponseDTO.setCountry(countryMapper.convert(address.getCountry()));
        }

        if (address.getCity() != null) {
            addressResponseDTO.setCity(cityMapper.convertWithoutCountry(address.getCity()));
        }

        if (address.getDistrict() != null) {
            addressResponseDTO.setDistrict(districtMapper.convertWithoutCity(address.getDistrict()));
        }

        return addressResponseDTO;
    }
}
