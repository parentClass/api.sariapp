package com.dnc.sariapi.services;

import com.dnc.sariapi.models.dtos.ProductSubCategoryDTO;
import org.springframework.data.domain.Page;

public interface ProductSubCategoryService {
    ProductSubCategoryDTO create(ProductSubCategoryDTO productSubCategoryDTO);

    Page<ProductSubCategoryDTO> getAllProductSubCategories(Integer maxResult, Integer page);

    ProductSubCategoryDTO getSubCategoryById(String subCategoryId);

    ProductSubCategoryDTO update(String subCategoryId, ProductSubCategoryDTO body);

    ProductSubCategoryDTO remove(String subCategoryId);

    ProductSubCategoryDTO activateSubCategory(String subCategoryId);
}
