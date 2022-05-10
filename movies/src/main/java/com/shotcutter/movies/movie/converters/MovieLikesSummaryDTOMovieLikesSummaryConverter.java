package com.shotcutter.movies.movie.converters;

import com.shotcutter.movies.movie.models.MovieLikesSummary;
import com.shotcutter.movies.movie.models.MovieLikesSummaryDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MovieLikesSummaryDTOMovieLikesSummaryConverter implements Converter<MovieLikesSummaryDTO, MovieLikesSummary> {

    @Override
    public MovieLikesSummary convert(MovieLikesSummaryDTO movieLikesSummary) {
        return MovieLikesSummary
                .builder()
                .likedByCurrentUser(movieLikesSummary.getLikedByCurrentUser())
                .dislikedByCurrentUser(movieLikesSummary.getDislikedByCurrentUser())
                .relatedMovieId(movieLikesSummary.getRelatedMovieId())
                .amountOfLikes(movieLikesSummary.getAmountOfLikes())
                .amountOfDislikes(movieLikesSummary.getAmountOfDislikes())
                .build();
    }
}
