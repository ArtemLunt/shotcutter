package com.shotcutter.movies.movie.converters;

import com.shotcutter.movies.movie.entities.search.MovieSearchEntity;
import com.shotcutter.movies.movie.models.MovieSearchDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MovieSearchEntityMovieSearchDTOConverter implements Converter<MovieSearchEntity, MovieSearchDTO> {

    @Override
    public MovieSearchDTO convert(MovieSearchEntity movie) {
        return MovieSearchDTO.builder()
                .id(movie.getId())
                .adult(movie.getAdult())
                .popularity(movie.getPopularity())
                .genres(movie.getGenres())
                .releaseDate(movie.getReleaseDate())
                .posterPath(movie.getPosterPath())
                .overview(movie.getOverview())
                .originalTitle(movie.getOriginalTitle())
                .originalLanguage(movie.getOriginalLanguage())
                .title(movie.getTitle())
                .build();
    }

}
