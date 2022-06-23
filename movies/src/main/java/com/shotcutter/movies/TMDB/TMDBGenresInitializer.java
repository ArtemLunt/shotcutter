package com.shotcutter.movies.TMDB;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@ConditionalOnProperty(value = "tmdb.integration.init")
public class TMDBGenresInitializer {

    private final TMDBGenresService genreService;
    private final TMDBService tmdbService;

    public TMDBGenresInitializer(TMDBGenresService genreService,
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
