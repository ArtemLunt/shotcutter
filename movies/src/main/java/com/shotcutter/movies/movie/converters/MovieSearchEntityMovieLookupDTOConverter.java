package com.shotcutter.movies.movie.converters;

import com.shotcutter.movies.movie.models.MovieLookupDTO;
import com.shotcutter.movies.movie.entities.search.MovieSearchEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MovieSearchEntityMovieLookupDTOConverter implements Converter<MovieSearchEntity, MovieLookupDTO> {

    @Override
    public MovieLookupDTO convert(MovieSearchEntity movieSearchEntity) {
        return MovieLookupDTO.builder()
                .id(movieSearchEntity.getId())
                .genres(movieSearchEntity.getGenres())
                .posterPath(movieSearchEntity.getPosterPath())
                .overview(movieSearchEntity.getOverview())
                .title(movieSearchEntity.getTitle())
                .build();
    }
}
