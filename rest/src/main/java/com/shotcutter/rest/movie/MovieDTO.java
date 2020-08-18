package com.shotcutter.rest.movie;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class MovieDTO {
    @Getter @Setter private Long id;
    @Getter @Setter private Boolean adult;
    @Getter @Setter private Integer voteCount;
    @Getter @Setter private Integer voteAverage;
    @Getter @Setter private Integer popularity;
    @Getter @Setter private List<String> genres;
    @Getter @Setter private Date releaseDate;
    @Getter @Setter private String posterPath;
    @Getter @Setter private String overview;
    @Getter @Setter private String originalTitle;
    @Getter @Setter private String originalLanguage;
    @Getter @Setter private String title;
    @Getter @Setter private String backdropPath;
}
