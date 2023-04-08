package com.dnc.sariapi.repositories;

import com.dnc.sariapi.models.dtos.MerchantDTO;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantRepository extends MongoRepository<MerchantDTO, String> {
    @Query("{'accountId': ?0, isDeleted: false}")
    Optional<MerchantDTO> findByAccountId(String accountId);

    @Query("{$or: [{'merchant_name': /?0/}, {'merchant_address': /?0/}], 'isDeleted': false, 'isActivated': true}")
    Page<MerchantDTO> searchAllMerchant(String query, Pageable pageable);

    @Query("{'_id': ?0, 'isActivated': true}")
    @Override
    @NonNull
    Optional<MerchantDTO> findById(String id);
}
