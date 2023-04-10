package com.dnc.sariapi.services;

import com.dnc.sariapi.models.dtos.MerchantCategoryDTO;
import org.springframework.data.domain.Page;

public interface MerchantCategoryService {
    MerchantCategoryDTO create(MerchantCategoryDTO productCategoryDTO);

    Page<MerchantCategoryDTO> getAllMerchantCategories(Integer maxResult, Integer page);

    MerchantCategoryDTO getCategoryById(String categoryId);

    MerchantCategoryDTO update(String categoryId, MerchantCategoryDTO body);

    MerchantCategoryDTO remove(String categoryId);

    MerchantCategoryDTO activateCategory(String categoryId);
}
