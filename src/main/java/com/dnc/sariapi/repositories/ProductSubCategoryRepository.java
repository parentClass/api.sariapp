package com.dnc.sariapi.repositories;

import com.dnc.sariapi.models.dtos.ProductCategoryDTO;
import com.dnc.sariapi.models.dtos.ProductSubCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSubCategoryRepository extends MongoRepository<ProductSubCategoryDTO, String> {
    @Query("{'merchantId': ?0, 'isDeleted': false}")
    Page<ProductSubCategoryDTO> findAllByMerchantId(String merchantId, Pageable pageable);

    @Query("{'merchantId': ?0, 'isDeleted': false}")
    List<ProductSubCategoryDTO> findAllByMerchantId(String merchantId);

    @Query("{'_id': ?0, 'merchantId': ?1, 'isDeleted': false}")
    Optional<ProductSubCategoryDTO> findBySubCategoryIdAndMerchantId(String subCategoryId, String merchantId);
}