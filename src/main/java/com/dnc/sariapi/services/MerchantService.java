package com.dnc.sariapi.services;

import com.dnc.sariapi.models.dtos.MerchantDTO;
import org.springframework.data.domain.Page;

public interface MerchantService {
    MerchantDTO create(MerchantDTO merchantDTO);
    Page<MerchantDTO> list(Integer maxResult, Integer page);
    Page<MerchantDTO> search(Integer maxResult, Integer page, String query);
    MerchantDTO findById(String id);
}
