package com.shotcutter.rest.TMDB;

import com.shotcutter.rest.shared.constant.EnvironmentVariable;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpMethod;

import java.util.Collection;
import java.util.Arrays;

@Service
public class TMDBService {
    private final static String BASE_URL = "https://api.themoviedb.org/3";
    private RestTemplate restTemplate;

    TMDBService(
            RestTemplateBuilder restTemplateBuilder,
            @Value(EnvironmentVariable.TMDB_API_KEY) String apiKey
    ) {
        restTemplate = restTemplateBuilder.build();
        restTemplate.setInterceptors(Arrays.asList(
                TMDBApiInterceptor.builder()
                    .apiKey(apiKey)
                    .build()
        ));
    }

    Collection<TMDBGenreDTO> getGenres() {
        return restTemplate.exchange(
                BASE_URL + Endpoint.GENRES.toString(),
                HttpMethod.GET,
                null,
                TMDBGenresPageDTO.class
        ).getBody().getGenres();
    }

    TMDBPage<TMDBMovieDTO> getPopularMovies(int page) {
        return restTemplate.exchange(
                UriComponentsBuilder.fromHttpUrl(BASE_URL + Endpoint.POPULAR_MOVIES.toString())
                        .queryParam(PathVariable.PAGE.toString(), page)
                        .build()
                        .toString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TMDBPage<TMDBMovieDTO>>() {}
        ).getBody();
    }
}
