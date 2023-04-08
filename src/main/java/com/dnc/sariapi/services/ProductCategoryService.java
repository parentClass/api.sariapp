package com.dnc.sariapi.services;

import com.dnc.sariapi.models.dtos.ProductCategoryDTO;
import org.springframework.data.domain.Page;

public interface ProductCategoryService {
    ProductCategoryDTO create(ProductCategoryDTO productCategoryDTO);

    Page<ProductCategoryDTO> getAllProductCategories(Integer maxResult, Integer page);

    ProductCategoryDTO getCategoryById(String categoryId);

    ProductCategoryDTO update(String categoryId, ProductCategoryDTO body);

    ProductCategoryDTO remove(String categoryId);

    ProductCategoryDTO activateCategory(String categoryId);
}
