package com.shotcutter.movies.movie.converters;

import com.shotcutter.movies.movie.entities.db.MovieLikeDBEntity;
import com.shotcutter.movies.movie.models.MovieLikeDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MovieLikeDTOMovieLikeDBEntityConverter implements Converter<MovieLikeDTO, MovieLikeDBEntity> {
    @Override
    public MovieLikeDBEntity convert(MovieLikeDTO movieLikeDTO) {
        return MovieLikeDBEntity
                .builder()
                .id(movieLikeDTO.getId())
                .relatedMovieId(movieLikeDTO.getRelatedMovieId())
                .relatedUserId(movieLikeDTO.getRelatedUserId())
                .updateTime(movieLikeDTO.getUpdateTime())
                .value(movieLikeDTO.getValue())
                .build();
    }
}
