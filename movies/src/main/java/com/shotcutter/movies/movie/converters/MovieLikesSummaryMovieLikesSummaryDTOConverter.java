package com.shotcutter.movies.movie.converters;

import com.shotcutter.movies.movie.models.MovieLikesSummary;
import com.shotcutter.movies.movie.models.MovieLikesSummaryDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MovieLikesSummaryMovieLikesSummaryDTOConverter implements Converter<MovieLikesSummary, MovieLikesSummaryDTO> {
    @Override
    public MovieLikesSummaryDTO convert(MovieLikesSummary movieLikesSummary) {
        return MovieLikesSummaryDTO
                .builder()
                .relatedMovieId(movieLikesSummary.getRelatedMovieId())
                .likedByCurrentUser(movieLikesSummary.getLikedByCurrentUser())
                .dislikedByCurrentUser(movieLikesSummary.getDislikedByCurrentUser())
                .amountOfLikes(movieLikesSummary.getAmountOfLikes())
                .amountOfDislikes(movieLikesSummary.getAmountOfDislikes())
                .build();
    }
}
