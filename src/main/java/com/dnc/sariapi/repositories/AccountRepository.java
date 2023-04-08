package com.dnc.sariapi.repositories;

import com.dnc.sariapi.models.dtos.AccountDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<AccountDTO, String> {
    @Query("{'username': ?0, 'isDeleted': false}")
    Optional<AccountDTO> findUserByUsername(String username);

    @Query("{'username': ?0, 'password': ?1}")
    Optional<AccountDTO> findUserByUsernameAndPassword(String username, String password);

    @Override
    @Query("{'_id': ?0, 'isDeleted': true}")
    void deleteById(String id);
}
