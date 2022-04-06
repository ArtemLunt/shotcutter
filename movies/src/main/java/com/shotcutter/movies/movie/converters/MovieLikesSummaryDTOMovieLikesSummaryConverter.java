package com.shotcutter.movies.movie.converters;

import com.shotcutter.movies.movie.entities.db.MovieLikeDBEntity;
import com.shotcutter.movies.movie.models.MovieLikeDTO;
import com.shotcutter.movies.movie.models.MovieLikesSummary;
import com.shotcutter.movies.movie.models.MovieLikesSummaryDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MovieLikesSummaryDTOMovieLikesSummaryConverter implements Converter<MovieLikesSummaryDTO, MovieLikesSummary> {

    @Override
    public MovieLikesSummary convert(MovieLikesSummaryDTO movieLikesSummary) {
        return MovieLikesSummary
                .builder()
                .relatedMovieId(movieLikesSummary.getRelatedMovieId())
                .amountOfLikes(movieLikesSummary.getAmountOfLikes())
                .amountOfDislikes(movieLikesSummary.getAmountOfDislikes())
                .build();
    }
}
