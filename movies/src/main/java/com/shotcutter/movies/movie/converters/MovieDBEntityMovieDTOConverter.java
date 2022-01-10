package com.shotcutter.movies.movie.converters;

import com.shotcutter.movies.movie.entities.db.MovieDBEntity;
import com.shotcutter.movies.movie.models.MovieDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MovieDBEntityMovieDTOConverter implements Converter<MovieDBEntity, MovieDTO> {

    @Override
    public MovieDTO convert(MovieDBEntity movie) {
        return MovieDTO.builder()
                .id(movie.getId())
                .adult(movie.getAdult())
                .voteCount(movie.getVoteCount())
                .voteAverage(movie.getVoteAverage())
                .popularity(movie.getPopularity())
                .genres(movie.getGenres())
                .releaseDate(movie.getReleaseDate())
                .posterPath(movie.getPosterPath())
                .overview(movie.getOverview())
                .originalTitle(movie.getOriginalTitle())
                .originalLanguage(movie.getOriginalLanguage())
                .title(movie.getTitle())
                .backdropPath(movie.getBackdropPath())
                .build();
    }

}
