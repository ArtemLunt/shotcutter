package com.shotcutter.movies.movie.converters;

import com.shotcutter.movies.movie.Movie;
import com.shotcutter.movies.movie.MovieLookupDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MovieMovieLookupDTOConverter implements Converter<Movie, MovieLookupDTO> {

    @Override
    public MovieLookupDTO convert(Movie movie) {
        return MovieLookupDTO.builder()
                .id(movie.getId())
                .genres(movie.getGenres())
                .posterPath(movie.getPosterPath())
                .overview(movie.getOverview())
                .title(movie.getTitle())
                .build();
    }

}
