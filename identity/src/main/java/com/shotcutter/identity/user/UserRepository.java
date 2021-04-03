package com.shotcutter.identity.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, Long> {

    Optional<UserEntity> findById(String id);
    Optional<UserEntity> findByEmail(String email);

}
