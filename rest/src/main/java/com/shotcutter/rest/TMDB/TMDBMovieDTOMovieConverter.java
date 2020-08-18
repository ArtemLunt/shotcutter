package com.shotcutter.rest.TMDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.shotcutter.rest.movie.GenreService;
import com.shotcutter.rest.movie.Movie;

import java.util.stream.Collectors;

@Component
public class TMDBMovieDTOMovieConverter implements Converter<TMDBMovieDTO, Movie> {
    @Autowired
    private GenreService genreService;

    @Override
    public Movie convert(TMDBMovieDTO tmdbMovieDTO) {
        return Movie.builder()
                .id(tmdbMovieDTO.getId())
                .adult(tmdbMovieDTO.getAdult())
                .voteCount(tmdbMovieDTO.getVote_count())
                .voteAverage(tmdbMovieDTO.getVote_average())
                .popularity(tmdbMovieDTO.getPopularity())
                .genres(
                        tmdbMovieDTO.getGenre_ids().stream()
                                .map(genreId -> genreService.getGenresMap().get(genreId))
                                .collect(Collectors.toSet())
                )
                .releaseDate(tmdbMovieDTO.getRelease_date())
                .posterPath(tmdbMovieDTO.getPoster_path())
                .overview(tmdbMovieDTO.getOverview())
                .originalTitle(tmdbMovieDTO.getOriginal_title())
                .originalLanguage(tmdbMovieDTO.getOriginal_language())
                .title(tmdbMovieDTO.getTitle())
                .backdropPath(tmdbMovieDTO.getBackdrop_path())
                .build();
    }
}
