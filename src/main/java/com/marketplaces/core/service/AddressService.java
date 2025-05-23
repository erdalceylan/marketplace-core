package com.marketplaces.core.service;

import com.marketplaces.core.entity.Address;
import com.marketplaces.core.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public Address get(Long id) {
        return addressRepository.findById(id).orElse(null);
    }
}
