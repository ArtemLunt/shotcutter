package com.shotcutter.movies.api.v1;

import com.shotcutter.movies.movie.services.GenreService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/genres")
public class GenresController {
    
    private final GenreService genreService;

    public GenresController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    Mono<List<String>> getGenres() {
        return genreService.getGenres().collect(Collectors.toList());
    }

}
