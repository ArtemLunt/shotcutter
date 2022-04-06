package com.shotcutter.movies.api.v1;

import com.shotcutter.library.converter.ConverterService;
import com.shotcutter.movies.movie.models.MovieLikeDTO;
import com.shotcutter.movies.movie.models.MovieLikesSummaryDTO;
import com.shotcutter.movies.movie.services.MovieLikeService;
import com.shotcutter.securitystarter.security.JWTPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/likes")
public class MovieLikeController {

    private final MovieLikeService movieLikeService;
    private final ConverterService converterService;

    public MovieLikeController(MovieLikeService movieLikeService, ConverterService converterService) {
        this.movieLikeService = movieLikeService;
        this.converterService = converterService;
    }

    @GetMapping(value = "/{relatedMovieId}")
    public Mono<MovieLikesSummaryDTO> getMovieLikesSummary(@PathVariable Long relatedMovieId,
                                                           JWTPrincipal principal) {
        return movieLikeService
                .getMovieLikesSummary(principal.getPrincipal().getId(), relatedMovieId)
                .map(likesSummary -> converterService.convertTo(likesSummary, MovieLikesSummaryDTO.class));
    }

    @PutMapping("/like/{relatedMovieId}")
    public Mono<MovieLikeDTO> likeMovie(@PathVariable Long relatedMovieId,
                                        @RequestParam(required = true) boolean value,
                                        JWTPrincipal principal) {
        return movieLikeService
                .likeMovie(principal.getPrincipal().getId(), relatedMovieId, value)
                .map(like -> converterService.convertTo(like, MovieLikeDTO.class));
    }

    @DeleteMapping("/like/{relatedMovieId}")
    public Mono<HttpStatus> deleteLike(@PathVariable Long relatedMovieId,
                                      JWTPrincipal principal) {
        return movieLikeService
                .deleteLike(principal.getPrincipal().getId(), relatedMovieId)
                .map(voidVal -> HttpStatus.OK);
    }

}
