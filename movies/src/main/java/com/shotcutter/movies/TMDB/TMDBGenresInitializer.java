package com.shotcutter.movies.TMDB;

import com.shotcutter.movies.movie.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class TMDBGenresInitializer {

    private final GenreService genreService;
    private final TMDBService tmdbService;

    public TMDBGenresInitializer(GenreService genreService,
                                 TMDBService tmdbService) {
        this.genreService = genreService;
        this.tmdbService = tmdbService;
    }

    /**
     * Initializes genres set.
     * Also initializes a genre map for simpler access to genres by id
     */
    @EventListener(ApplicationReadyEvent.class)
    @Async(value = "tmdb-integration")
    public void initializeGenresSet() {
        log.info("Initialization with the TMDB service started");

        genreService.setGenresMap(
                tmdbService.getGenres().stream()
                        .collect(Collectors.toMap(TMDBGenreDTO::getId, TMDBGenreDTO::getName))
        );

        log.info("Genres set successfully initialized");
    }

}
