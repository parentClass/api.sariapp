package com.dnc.sariapi.services.impl;

import com.dnc.sariapi.models.dtos.MerchantDTO;
import com.dnc.sariapi.repositories.MerchantRepository;
import com.dnc.sariapi.services.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Override
    public MerchantDTO create(MerchantDTO merchantDTO) {
        return merchantRepository.save(merchantDTO);
    }

    @Override
    public Page<MerchantDTO> list(Integer maxResult, Integer page) {
        return merchantRepository.findAll(PageRequest.of(page, maxResult));
    }

    @Override
    public Page<MerchantDTO> search(Integer maxResult, Integer page, String query) {
        return merchantRepository.searchAllMerchant(query, PageRequest.of(page, maxResult));
    }

    @Override
    public MerchantDTO findById(String id) {
        return merchantRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Merchant is non existent"));
    }
}
