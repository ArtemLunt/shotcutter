package com.shotcutter.movies.TMDB;

import com.shotcutter.library.converter.ConverterService;
import com.shotcutter.movies.movie.MovieService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.shotcutter.movies.movie.GenreService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(value = "tmdb.integration.init")
public class TMDBIntegrator {

    private final ConverterService converterService;
    private final GenreService genreService;
    private final TMDBService tmdbService;

    private final int pageSize;
    private int currentPage;

    TMDBIntegrator(ConverterService converterService,
                   GenreService genreService,
                   TMDBService tmdbService,
                   MovieService movieService
    ) {
        this.converterService = converterService;
        this.genreService = genreService;
        this.tmdbService = tmdbService;

        pageSize = 20;
        currentPage = movieService.count().block().intValue() / pageSize;

        if (currentPage == 0) {
            currentPage = 1;
        }
    }

    /**
     * Loads movies chunk
     */
    @Scheduled(
            fixedDelayString = "${tmdb.integration.delay}",
            initialDelayString = "${tmdb.integration.delay}"
    )
    @Async(value = "tmdb-integration")
    public void loadMoviesChunk() {
        if (genreService.getGenresMap() == null) {
            log.info("Waiting for genres initialization");
            return;
        }

        tmdbService
                .getPopularMovies(currentPage++)
                .getResults()
                .forEach(movie -> tmdbService.saveMovie(movie).block());
        log.info("Movies chunk successfully loaded");
    }

}
