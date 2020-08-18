package com.shotcutter.rest.TMDB;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Async;
import com.shotcutter.rest.shared.ConverterService;
import com.shotcutter.rest.movie.MovieRepository;
import org.springframework.stereotype.Component;
import com.shotcutter.rest.movie.GenreService;
import com.shotcutter.rest.movie.Movie;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Slf4j
@Component
@ConditionalOnProperty(value = "tmdb.integration.init", matchIfMissing = false)
public class TMDBIntegrator {
    @Autowired private ConverterService converterService;
    @Autowired private GenreService genreService;
    @Autowired private TMDBService tmdbService;

    @Autowired private MovieRepository movieRepository;

    private final int pageSize;
    private int currentPage;

    TMDBIntegrator(MovieRepository movieRepository) {
        pageSize = 20;
        currentPage = ((int) movieRepository.count()) / pageSize;
    }

    /**
     * Initializes genres set.
     * Also initializes a genre map for simpler access to genres by id
     */
    @Async
    @PostConstruct
    protected void initializeGenresSet() {
        log.info("Initialization with the TMDB service started");

        genreService.setGenresMap(
                tmdbService.getGenres().stream()
                        .collect(
                                Collectors.toMap(
                                        TMDBGenreDTO::getId,
                                        TMDBGenreDTO::getName
                                ))
        );

        log.info("Genres set successfully initialized");
    }

    /**
     * Loads movies chunk
     */
    @Async
    @Scheduled(
            fixedDelayString = "${tmdb.integration.delay}",
            initialDelayString = "${tmdb.integration.delay}"
    )
    protected void loadMoviesChunk() {
        if (genreService.getGenresMap() == null) {
            return;
        }

        movieRepository.saveAll(
                tmdbService.getPopularMovies(currentPage++)
                        .getResults()
                        .parallelStream()
                        .map(tmdbMovieDTO -> converterService.convertTo(tmdbMovieDTO, Movie.class).get())
                        .collect(Collectors.toList())
        );

        log.info("Movies chunk successfully loaded");
    }
}
