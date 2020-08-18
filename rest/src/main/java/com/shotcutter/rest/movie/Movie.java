package com.shotcutter.rest.movie;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Builder
@Document(collection = "movies")
public class Movie {
    @Id
    @Getter @Setter private Long id;
    @Getter @Setter private Boolean adult;
    @Getter @Setter private Long voteCount;
    @Getter @Setter private Double voteAverage;
    @Getter @Setter private Long popularity;
    @Getter @Setter private Set<String> genres;
    @Getter @Setter private Date releaseDate;
    @Getter @Setter private String posterPath;
    @Getter @Setter private String overview;
    @Getter @Setter private String originalTitle;
    @Getter @Setter private String originalLanguage;
    @Getter @Setter private String title;
    @Getter @Setter private String backdropPath;
}
