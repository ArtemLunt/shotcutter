package com.shotcutter.movies.movie.repositories.db;

import com.shotcutter.movies.movie.entities.db.MovieDBEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovieMongoRepository extends ReactiveMongoRepository<MovieDBEntity, Long> {
}
