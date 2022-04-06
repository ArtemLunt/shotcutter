package com.shotcutter.movies.movie.converters;

import com.shotcutter.movies.movie.entities.db.MovieLikeDBEntity;
import com.shotcutter.movies.movie.models.MovieLikeDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MovieLikeDBEntityMovieLikeDTOConverter implements Converter<MovieLikeDBEntity, MovieLikeDTO> {
    @Override
    public MovieLikeDTO convert(MovieLikeDBEntity movieLikeDBEntity) {
        return MovieLikeDTO
                .builder()
                .id(movieLikeDBEntity.getId())
                .relatedMovieId(movieLikeDBEntity.getRelatedMovieId())
                .relatedUserId(movieLikeDBEntity.getRelatedUserId())
                .updateTime(movieLikeDBEntity.getUpdateTime())
                .value(movieLikeDBEntity.getValue())
                .build();
    }
}
