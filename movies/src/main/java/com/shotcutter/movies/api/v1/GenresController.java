package com.shotcutter.movies.api.v1;

import com.shotcutter.movies.movie.services.GenreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/genres")
public class GenresController {
    
    private final GenreService genreService;

    public GenresController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    Iterable<String> getGenres() {
        return genreService.getGenresMap().values();
    }

}
