package com.shotcutter.movies.TMDB.converters;

import com.shotcutter.movies.TMDB.TMDBGenresService;
import com.shotcutter.movies.TMDB.TMDBMovieDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.shotcutter.movies.movie.entities.db.MovieDBEntity;

import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(value = "tmdb.integration.init")
public class TMDBMovieDTOMovieDBEntityConverter implements Converter<TMDBMovieDTO, MovieDBEntity> {
    private final TMDBGenresService genreService;

    TMDBMovieDTOMovieDBEntityConverter(TMDBGenresService genreService) {
        this.genreService = genreService;
    }

    @Override
    public MovieDBEntity convert(TMDBMovieDTO tmdbMovieDTO) {
        return MovieDBEntity.builder()
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
