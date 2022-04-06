package com.shotcutter.movies.movie.services;

import com.shotcutter.movies.movie.entities.db.MovieLikeDBEntity;
import com.shotcutter.movies.movie.models.MovieLikesSummary;
import com.shotcutter.movies.movie.repositories.db.MovieLikeInfoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class MovieLikeService {

    private final MovieLikeInfoRepository movieLikeInfoRepository;

    public MovieLikeService(MovieLikeInfoRepository movieLikeInfoRepository) {
        this.movieLikeInfoRepository = movieLikeInfoRepository;
    }

    public Mono<MovieLikesSummary> getMovieLikesSummary(String relatedUserId, Long relatedMovieId) {
        return Mono.zip(
                        movieLikeInfoRepository
                                .countAllByRelatedMovieIdAndRelatedUserIdAndValue(relatedMovieId, relatedUserId, Boolean.TRUE)
                                .defaultIfEmpty(0l),
                        movieLikeInfoRepository
                                .countAllByRelatedMovieIdAndRelatedUserIdAndValue(relatedMovieId, relatedUserId, Boolean.FALSE)
                                .defaultIfEmpty(0l)
                )
                .flatMap(tuple -> {
                    var likesSummary = MovieLikesSummary
                            .builder()
                            .relatedMovieId(relatedMovieId)
                            .amountOfLikes(tuple.getT1())
                            .amountOfDislikes(tuple.getT2());

                    return movieLikeInfoRepository
                            .findFirstByRelatedUserIdAndRelatedMovieId(relatedUserId, relatedMovieId)
                            .map(likesSummary::likeByCurrentUser)
                            .defaultIfEmpty(likesSummary)
                            .map(MovieLikesSummary.MovieLikesSummaryBuilder::build);
                });
    }

    public Mono<Void> deleteLike(String relatedUserId, Long relatedMovieId) {
        return movieLikeInfoRepository.deleteByRelatedUserIdAndRelatedMovieId(relatedUserId, relatedMovieId);
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

}
