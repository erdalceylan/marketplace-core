package com.marketplaces.core.manager;

import com.marketplaces.core.dto.response.MerchantResponseDTO;
import com.marketplaces.core.entity.Merchant;
import com.marketplaces.core.mapper.MerchantMapper;
import com.marketplaces.core.service.MerchantService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantManager {

    private final MerchantService merchantService;
    private final MerchantMapper merchantMapper;

    MerchantManager(MerchantService merchantService,
                    MerchantMapper merchantMapper) {
        this.merchantService = merchantService;
        this.merchantMapper = merchantMapper;
    }

    public List<MerchantResponseDTO> getTopList() {
        List<Merchant> merchants =  this.merchantService.getTopList(100, 0);
        return merchants.stream().map(this.merchantMapper::convert).toList();
    }
}
