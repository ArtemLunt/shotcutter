package com.shotcutter.rest.TMDB;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Async;
import com.shotcutter.rest.shared.ConverterService;
import com.shotcutter.rest.movie.MovieRepository;
import org.springframework.stereotype.Component;
import com.shotcutter.rest.movie.GenreService;
import com.shotcutter.rest.movie.Movie;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Component
@EnableScheduling
@ConditionalOnProperty(value = "tmdb.integration.init", matchIfMissing = false)
public class TMDBIntegrator {
    private Logger logger;

    @Autowired private ConverterService converterService;
    @Autowired private GenreService genreService;
    @Autowired private TMDBService tmdbService;

    @Autowired private MovieRepository movieRepository;

    private final int pageSize;
    private int currentPage;

    TMDBIntegrator(MovieRepository movieRepository) {
        logger = LoggerFactory.getLogger(TMDBIntegrator.class);

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
        logger.info("Initialization with the TMDB service started");

        genreService.setGenresMap(
                tmdbService.getGenres().stream()
                        .collect(
                                Collectors.toMap(
                                        TMDBGenreDTO::getId,
                                        TMDBGenreDTO::getName
                                ))
        );

        logger.info("Genres set successfully initialized");
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

        logger.info("Movies chunk successfully loaded");
    }
}
