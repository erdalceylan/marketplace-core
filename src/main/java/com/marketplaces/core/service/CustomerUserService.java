package com.marketplaces.core.service;

import com.marketplaces.core.entity.CustomerUser;
import com.marketplaces.core.repository.CustomerUserRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserService {

    private final CustomerUserRepository customerUserRepository;

    CustomerUserService(CustomerUserRepository customerUserRepository) {
        this.customerUserRepository = customerUserRepository;
    }

    public CustomerUser save(CustomerUser customerUser) {
        return customerUserRepository.save(customerUser);
    }

    public CustomerUser findById(Long id) {
        return customerUserRepository.findById(id).orElse(null);
    }

    public CustomerUser getReference(Long id) {
        return customerUserRepository.getReferenceById(id);
    }
}
