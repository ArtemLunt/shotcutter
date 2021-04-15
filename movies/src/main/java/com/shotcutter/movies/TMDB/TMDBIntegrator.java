package com.shotcutter.movies.TMDB;

import com.shotcutter.library.converter.ConverterService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import com.shotcutter.movies.movie.MovieRepository;
import org.springframework.stereotype.Component;
import com.shotcutter.movies.movie.GenreService;
import com.shotcutter.movies.movie.Movie;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
@Component
@ConditionalOnProperty(value = "tmdb.integration.init")
public class TMDBIntegrator {
    private ConverterService converterService;
    private GenreService genreService;
    private TMDBService tmdbService;

    private MovieRepository movieRepository;

    private final int pageSize;
    private int currentPage;

    TMDBIntegrator(
            ConverterService converterService,
            GenreService genreService,
            TMDBService tmdbService,
            MovieRepository movieRepository
    ) {
        this.converterService = converterService;
        this.genreService = genreService;
        this.tmdbService = tmdbService;
        this.movieRepository = movieRepository;

        pageSize = 20;
        currentPage = ((int) movieRepository.count()) / pageSize;

        if (currentPage == 0) {
            currentPage = 1;
        }
    }

    /**
     * Initializes genres set.
     * Also initializes a genre map for simpler access to genres by id
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initializeGenresSet() {
        log.info("Initialization with the TMDB service started");

        genreService.setGenresMap(
                tmdbService.getGenres().stream()
                        .collect(Collectors.toMap(TMDBGenreDTO::getId, TMDBGenreDTO::getName))
        );

        log.info("Genres set successfully initialized");
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

        movieRepository.saveAll(
                tmdbService.getPopularMovies(currentPage++)
                        .getResults()
                        .stream()
                        .map(tmdbMovieDTO -> converterService.convertTo(tmdbMovieDTO, Movie.class).get())
                        .collect(Collectors.toList())
        );

        log.info("Movies chunk successfully loaded");
    }
}
