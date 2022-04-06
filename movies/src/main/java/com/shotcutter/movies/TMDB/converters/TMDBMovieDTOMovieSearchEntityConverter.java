package com.shotcutter.movies.TMDB.converters;

import com.shotcutter.movies.TMDB.TMDBMovieDTO;
import com.shotcutter.movies.movie.services.GenreService;
import com.shotcutter.movies.movie.entities.search.MovieSearchEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TMDBMovieDTOMovieSearchEntityConverter implements Converter<TMDBMovieDTO, MovieSearchEntity> {
    private final GenreService genreService;

    TMDBMovieDTOMovieSearchEntityConverter(GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    public MovieSearchEntity convert(TMDBMovieDTO tmdbMovieDTO) {
        var genres = tmdbMovieDTO.getGenre_ids().stream()
                .map(genreId -> genreService.getGenresMap().get(genreId))
                .collect(Collectors.toSet());

        return MovieSearchEntity.builder()
                .id(tmdbMovieDTO.getId())
                .adult(tmdbMovieDTO.getAdult())
                .popularity(tmdbMovieDTO.getPopularity())
                .genres(genres)
                .releaseDate(tmdbMovieDTO.getRelease_date())
                .posterPath(tmdbMovieDTO.getPoster_path())
                .overview(tmdbMovieDTO.getOverview())
                .originalTitle(tmdbMovieDTO.getOriginal_title())
                .originalLanguage(tmdbMovieDTO.getOriginal_language())
                .title(tmdbMovieDTO.getTitle())
                .build();
    }
}
