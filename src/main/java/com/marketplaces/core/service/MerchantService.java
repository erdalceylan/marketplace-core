package com.marketplaces.core.service;

import com.marketplaces.core.entity.Merchant;
import com.marketplaces.core.entity.ProductPool;
import com.marketplaces.core.repository.MerchantRepository;
import com.marketplaces.core.repository.ProductPoolRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantService {


    private final MerchantRepository merchantRepository;

    public MerchantService(
            MerchantRepository merchantRepository
    ){
        this.merchantRepository = merchantRepository;
    }

    public Merchant save(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    public Merchant findById(Long id) {
        return merchantRepository.findById(id).orElse(null);
    }

    public List<Merchant> getTopList(Integer limit, Integer offset) {
        Pageable topN = PageRequest.of(offset, limit);
        return merchantRepository.getTopList(topN);
    }
}
