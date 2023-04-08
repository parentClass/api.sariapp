package com.dnc.sariapi.repositories;

import com.dnc.sariapi.models.dtos.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<ProductDTO, String> {
    @Query("{'merchantId': ?0, 'isDeleted': false, 'isActivated': true}")
    Page<ProductDTO> findAllByMerchantId(String merchantId, Pageable pageable);

    @Query("{'_id': ?0, 'merchantId': ?1, 'isDeleted': false}")
    Optional<ProductDTO> findByProductIdAndMerchantId(String productId, String merchantId);

    @Override
    @Query("{'_id': ?0, 'isDeleted': false}")
    Optional<ProductDTO> findById(String productId);

    @Query("{'_id': ?0, 'isDeleted': false, 'storeId': ?1}")
    Optional<ProductDTO> findByStoreIdAndProductId(String productId, String storeId);
}
