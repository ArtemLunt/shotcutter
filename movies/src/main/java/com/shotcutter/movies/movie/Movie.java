package com.shotcutter.movies.movie;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@Document(collection = "movies")
public class Movie {
    @Id
    private Long id;
    private Boolean adult;
    private Long voteCount;
    private Double voteAverage;
    private Long popularity;
    @Indexed
    private Set<String> genres;
    private Date releaseDate;
    private String posterPath;
    @TextIndexed(weight = 2)
    private String overview;
    @TextIndexed(weight = 3)
    private String originalTitle;
    private String originalLanguage;
    @TextIndexed(weight = 3)
    private String title;
    private String backdropPath;
}
