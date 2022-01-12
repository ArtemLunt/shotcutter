package com.shotcutter.movies;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.boot.SpringApplication;

@EnableAsync
@EnableScheduling
@EnableReactiveMongoRepositories("com.shotcutter.**.repositories.db")
@EnableReactiveElasticsearchRepositories("com.shotcutter.**.repositories.search")
@SpringBootApplication
public class MoviesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesApplication.class, args);
    }

}
