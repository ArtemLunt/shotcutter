package com.shotcutter.identity.user;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<UserEntity, Long> {

    Mono<UserEntity> findById(String id);
    Mono<UserEntity> findByEmail(String email);

    @Override
    <S extends UserEntity> Mono<S> save(S s);
}
