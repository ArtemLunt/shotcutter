package com.shotcutter.movies.movie.services;

import com.shotcutter.movies.movie.entities.db.MovieLikeDBEntity;
import com.shotcutter.movies.movie.models.MovieLikesSummary;
import com.shotcutter.movies.movie.repositories.db.MovieLikeInfoRepository;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class MovieLikeService {

    private final MovieLikeInfoRepository movieLikeInfoRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public MovieLikeService(MovieLikeInfoRepository movieLikeInfoRepository,
                            ReactiveMongoTemplate reactiveMongoTemplate) {
        this.movieLikeInfoRepository = movieLikeInfoRepository;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public Mono<MovieLikesSummary> getMovieLikesSummary(String relatedUserId, Long relatedMovieId) {
        return Mono.zip(
                        getMovieLikesSummary(relatedMovieId),
                        existByRelatedUserIdAndRelatedMovieIdAndValue(relatedUserId, relatedMovieId, Boolean.TRUE),
                        existByRelatedUserIdAndRelatedMovieIdAndValue(relatedUserId, relatedMovieId, Boolean.FALSE)
                )
                .map(tuple -> (
                        tuple.getT1()
                                .withLikedByCurrentUser(tuple.getT2())
                                .withDislikedByCurrentUser(tuple.getT3())
                ));
    }

    public Mono<MovieLikesSummary> getMovieLikesSummary(Long relatedMovieId) {
        return Mono.zip(
                        movieLikeInfoRepository.countAllByRelatedMovieIdAndValue(relatedMovieId, Boolean.TRUE),
                        movieLikeInfoRepository.countAllByRelatedMovieIdAndValue(relatedMovieId, Boolean.FALSE)
                )
                .map(tuple -> MovieLikesSummary
                        .builder()
                        .likedByCurrentUser(null)
                        .dislikedByCurrentUser(null)
                        .relatedMovieId(relatedMovieId)
                        .amountOfLikes(tuple.getT1())
                        .amountOfDislikes(tuple.getT2())
                        .build());
    }

    public Mono<Void> deleteLike(String relatedUserId, Long relatedMovieId) {
        var criteria = Criteria.where("relatedMovieId")
                .is(relatedMovieId)
                .and("relatedUserId")
                .is(relatedUserId);
        return reactiveMongoTemplate.findAndRemove(Query.query(criteria), MovieLikeDBEntity.class)
                                    .then();
    }

    public Mono<MovieLikeDBEntity> likeMovie(String relatedUserId,
                                             Long relatedMovieId,
                                             boolean value) {
        return movieLikeInfoRepository
                // we need to fetch the existing like first
                // this logic allows to remove the existing like/dislike if user touches it twice
                .findFirstByRelatedUserIdAndRelatedMovieId(relatedUserId, relatedMovieId)
                .defaultIfEmpty(
                        MovieLikeDBEntity
                                .builder()
                                .relatedMovieId(relatedMovieId)
                                .relatedUserId(relatedUserId)
                                .build()
                )
                .map(movie -> movie.withValue(value).withUpdateTime(new Date()))
                .flatMap(movieLikeInfoRepository::save);
    }

    private Mono<Boolean> existByRelatedUserIdAndRelatedMovieIdAndValue(String relatedUserId, Long relatedMovieId, Boolean value) {
        return movieLikeInfoRepository
                .findFirstByRelatedUserIdAndRelatedMovieIdAndValue(relatedUserId, relatedMovieId, value)
                .map(like -> true)
                .defaultIfEmpty(false);
    }

}
