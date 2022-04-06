package com.shotcutter.movies.movie.services;

import com.shotcutter.movies.movie.entities.db.MovieDBEntity;
import com.shotcutter.movies.movie.entities.search.MovieSearchEntity;
import com.shotcutter.movies.movie.repositories.db.MovieMongoRepository;
import com.shotcutter.movies.movie.repositories.search.MovieElasticsearchRepository;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final ReactiveMongoTemplate mongoTemplate;
    private final MovieMongoRepository movieMongoRepository;

    private final ReactiveElasticsearchTemplate elasticsearchTemplate;
    private final MovieElasticsearchRepository movieElasticsearchRepository;

    public MovieService(ReactiveMongoTemplate mongoTemplate,
                        MovieMongoRepository movieMongoRepository,
                        ReactiveElasticsearchTemplate elasticsearchTemplate,
                        MovieElasticsearchRepository movieElasticsearchRepository) {
        this.movieMongoRepository = movieMongoRepository;
        this.mongoTemplate = mongoTemplate;
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.movieElasticsearchRepository = movieElasticsearchRepository;
    }

    public Mono<MovieDBEntity> findById(Long id) {
        return movieMongoRepository.findById(id);
    }

    public Mono<Page<MovieSearchEntity>> search(Pageable pageable, String searchKey, List<String> genres) {
        var baseBoolQueryBuilder = QueryBuilders.boolQuery();

        if (searchKey != null) {
            baseBoolQueryBuilder = baseBoolQueryBuilder.must(
                    QueryBuilders
                            .multiMatchQuery(searchKey, "title", "originalTitle")
                            .field("title", 1)
                            .field("originalTitle", 1)
                            .fuzziness(Fuzziness.TWO)
                            .maxExpansions(2)
            );
        }

        if (genres != null && genres.size() != 0) {
            var genreMatchQueryBuilder = genres
                    .stream()
                    .map(genre -> QueryBuilders.matchQuery("genres", genre))
                    .collect(Collector.of(
                            () -> QueryBuilders.boolQuery().minimumShouldMatch(1),
                            BoolQueryBuilder::should,
                            BoolQueryBuilder::should
                    ))
                    .minimumShouldMatch(1);
            baseBoolQueryBuilder = baseBoolQueryBuilder.must(genreMatchQueryBuilder);
        }

        var query = new NativeSearchQueryBuilder()
                .withQuery(baseBoolQueryBuilder)
                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("popularity").order(SortOrder.DESC))
                .withPageable(pageable)
                .build();

        var pageMono = elasticsearchTemplate
                .search(query, MovieSearchEntity.class)
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        var countMono = elasticsearchTemplate
                .count(query, MovieSearchEntity.class);

        return Mono.zip(pageMono, countMono)
                .map(tuple -> new PageImpl<MovieSearchEntity>(tuple.getT1(), pageable, tuple.getT2()));
    }

    public Mono<MovieDBEntity> saveToDb(MovieDBEntity movie) {
        return movieMongoRepository.save(movie);
    }

    public Mono<MovieSearchEntity> saveToSearchEngine(MovieSearchEntity movie) {
        return movieElasticsearchRepository.save(movie);
    }

    public Flux<MovieSearchEntity> lookup(String key) {
        var query = new NativeSearchQueryBuilder()
                .withQuery(getDefaultKeywordSearchQueryBuilder(key))
                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("popularity").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0, 10))
                .build();

        return elasticsearchTemplate
                .search(query, MovieSearchEntity.class)
                .map(SearchHit::getContent);
    }

    public Mono<Long> count() {
        return movieMongoRepository.count();
    }

    private QueryBuilder getDefaultKeywordSearchQueryBuilder(String query) {
        return QueryBuilders
                .multiMatchQuery(query.trim())
                .field("title", 5)
                .field("originalTitle", 3)
                .field("overview", 1)
                .fuzziness(Fuzziness.TWO)
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS);
    }
}
