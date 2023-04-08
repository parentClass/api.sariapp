package com.dnc.sariapi.services;

import com.dnc.sariapi.models.dtos.ProductDTO;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductDTO create(ProductDTO body);

    ProductDTO update(String productId, ProductDTO body);

    Page<ProductDTO> getAllProducts(Integer maxResult, Integer page);

    ProductDTO getProductById(String productId);

    ProductDTO remove(String productId);

    ProductDTO activateProduct(String productId);
}
