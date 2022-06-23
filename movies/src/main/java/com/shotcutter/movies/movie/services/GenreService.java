package com.shotcutter.movies.movie.services;

import com.shotcutter.movies.movie.entities.db.MovieDBEntity;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import lombok.Data;
import reactor.core.publisher.Flux;

@Data
@Service
public class GenreService {
    private final ReactiveMongoTemplate mongoTemplate;

    public Flux<String> getGenres() {
        return mongoTemplate.findDistinct("genres", MovieDBEntity.class, String.class);
    }
}
