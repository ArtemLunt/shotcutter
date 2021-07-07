package com.shotcutter.movies.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ReactiveMongoTemplate mongoTemplate;

    public MovieService(MovieRepository movieRepository,
                        ReactiveMongoTemplate mongoTemplate) {
        this.movieRepository = movieRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Mono<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    public Mono<Page<Movie>> search(Pageable pageable, String query, List<String> genres) {
        var baseQuery = new Query();

        if (query != null) {
            baseQuery = baseQuery.addCriteria(TextCriteria.forDefaultLanguage()
                                    .matchingPhrase(query)
                                    .diacriticSensitive(false)
                                    .caseSensitive(false));
        }

        if (genres != null && genres.size() != 0) {
            baseQuery = baseQuery.addCriteria(Criteria.where("genres").all(genres));
        }

        var pageMono = mongoTemplate.find(baseQuery.with(pageable), Movie.class)
                .collect(Collectors.toList());
        var countMono = mongoTemplate.count(baseQuery, Movie.class);

        return Mono.zip(pageMono, countMono)
                .map(tuple -> new PageImpl(tuple.getT1(), pageable, tuple.getT2()));
    }

    public Flux<Movie> lookup(String key) {
        var query = new Query()
                .addCriteria(TextCriteria.forDefaultLanguage().matchingPhrase(key))
                .with(Sort.by(Sort.Direction.ASC, "title"))
                .limit(10);

        return mongoTemplate.find(query, Movie.class);
    }

}
