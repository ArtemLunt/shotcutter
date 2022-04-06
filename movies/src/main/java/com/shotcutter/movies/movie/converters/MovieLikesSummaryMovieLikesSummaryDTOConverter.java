package com.shotcutter.movies.movie.converters;

import com.shotcutter.movies.movie.entities.db.MovieLikeDBEntity;
import com.shotcutter.movies.movie.models.MovieLikesSummary;
import com.shotcutter.movies.movie.models.MovieLikesSummaryDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MovieLikesSummaryMovieLikesSummaryDTOConverter implements Converter<MovieLikesSummary, MovieLikesSummaryDTO> {

    @Override
    public MovieLikesSummaryDTO convert(MovieLikesSummary movieLikesSummary) {
        return MovieLikesSummaryDTO
                .builder()
                .relatedMovieId(movieLikesSummary.getRelatedMovieId())
                .likedByCurrentUser(
                        Optional.ofNullable(movieLikesSummary.getLikeByCurrentUser())
                                .map(MovieLikeDBEntity::getValue)
                                .orElse(false)
                )
                .dislikedByCurrentUser(
                        Optional.ofNullable(movieLikesSummary.getLikeByCurrentUser())
                                .map(like -> !like.getValue())
                                .orElse(false)
                )
                .amountOfLikes(movieLikesSummary.getAmountOfLikes())
                .amountOfDislikes(movieLikesSummary.getAmountOfDislikes())
                .build();
    }
}
