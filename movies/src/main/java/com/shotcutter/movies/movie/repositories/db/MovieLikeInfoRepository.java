package com.shotcutter.movies.movie.repositories.db;

import com.shotcutter.movies.movie.entities.db.MovieLikeDBEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface MovieLikeInfoRepository extends ReactiveMongoRepository<MovieLikeDBEntity, String> {
    Mono<MovieLikeDBEntity> findFirstByRelatedUserIdAndRelatedMovieId(String relatedUserId, Long relatedMovieId);
    Mono<Void> deleteByRelatedUserIdAndRelatedMovieId(String relatedUserId, Long relatedMovieId);

    Mono<Long> countAllByRelatedMovieIdAndRelatedUserIdAndValue(Long relatedMovieId,
                                                                String relatedUserId,
                                                                Boolean value);
}
