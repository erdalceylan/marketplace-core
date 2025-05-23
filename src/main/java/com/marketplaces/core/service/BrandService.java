package com.marketplaces.core.service;

import com.marketplaces.core.entity.Brand;
import com.marketplaces.core.repository.BrandRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand findById(Integer id) {
        return brandRepository.findById(id).orElse(null);
    }

    public List<Brand> getTopList(Integer limit, Integer offset) {
        Pageable topN = PageRequest.of(offset, limit);
        return brandRepository.getTopList(topN);
    }
}
