package com.dnc.sariapi.repositories;

import com.dnc.sariapi.models.dtos.MerchantCategoryDTO;
import com.dnc.sariapi.models.dtos.ProductCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MerchantCategoryRepository extends MongoRepository<MerchantCategoryDTO, String> {
    @Override
    @Query("{'isDeleted': false, 'isActivated': true}")
    Page<MerchantCategoryDTO> findAll(Pageable pageable);
}
