package com.shotcutter.movies.movie.converters;

import com.shotcutter.movies.movie.entities.db.MovieDBEntity;
import com.shotcutter.movies.movie.models.MovieLookupDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MovieDBEntityMovieLookupDTOConverter implements Converter<MovieDBEntity, MovieLookupDTO> {
    @Override
    public MovieLookupDTO convert(MovieDBEntity movie) {
        return MovieLookupDTO.builder()
                .id(movie.getId())
                .genres(movie.getGenres())
                .posterPath(movie.getPosterPath())
                .overview(movie.getOverview())
                .title(movie.getTitle())
                .build();
    }
}
