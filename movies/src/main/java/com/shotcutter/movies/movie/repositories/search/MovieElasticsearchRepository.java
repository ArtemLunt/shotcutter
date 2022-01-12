package com.shotcutter.movies.movie.repositories.search;

import com.shotcutter.movies.movie.entities.search.MovieSearchEntity;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface MovieElasticsearchRepository extends ReactiveElasticsearchRepository<MovieSearchEntity, Long> {
}
