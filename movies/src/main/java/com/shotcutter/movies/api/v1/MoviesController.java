package com.shotcutter.movies.api.v1;

import com.shotcutter.library.converter.ConverterService;
import com.shotcutter.movies.movie.models.MovieDTO;
import com.shotcutter.movies.movie.models.MovieLookupDTO;
import com.shotcutter.movies.movie.services.MovieService;
import com.shotcutter.movies.movie.models.MovieSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("api/movies")
public class MoviesController {

    private final MovieService movieService;
    private final ConverterService converterService;

    public MoviesController(MovieService movieService,
                            ConverterService converterService) {
        this.movieService = movieService;
        this.converterService = converterService;
    }

    @GetMapping("/{id}")
    public Mono<MovieDTO> findById(@PathVariable Long id) {
        return movieService.findById(id)
                .map(movie -> converterService.convertTo(movie, MovieDTO.class));
    }

    @GetMapping("search")
    public Mono<Page<MovieSearchDTO>> search(@PageableDefault(page = 0, size = 40) Pageable pageable,
                                             @RequestParam(required = false) String query,
                                             @RequestParam(required = false) List<String> genres) {
        if (query == null && genres == null) {
            return Mono.just(Page.empty());
        }

        return movieService.search(pageable, query, genres)
                .map(page -> page.map(movie -> converterService.convertTo(movie, MovieSearchDTO.class)));
    }

    @GetMapping(value = "lookup")
    public Flux<MovieLookupDTO> lookup(@RequestParam(required = false) String query) {
        return movieService.lookup(query)
                .map(movie -> converterService.convertTo(movie, MovieLookupDTO.class));
    }

}
