package com.shotcutter.movies.movie.repositories.db;

import com.shotcutter.movies.movie.entities.db.MovieLikeDBEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieLikeInfoRepository extends ReactiveMongoRepository<MovieLikeDBEntity, Long> {
    Mono<MovieLikeDBEntity> findFirstByRelatedUserIdAndRelatedMovieId(String relatedUserId, Long relatedMovieId);

    Mono<MovieLikeDBEntity> findFirstByRelatedUserIdAndRelatedMovieIdAndValue(String relatedUserId, Long relatedMovieId, Boolean value);

    Mono<Long> countAllByRelatedMovieIdAndValue(Long relatedMovieId,
                                                Boolean value);
}
