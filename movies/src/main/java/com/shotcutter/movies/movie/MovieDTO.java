package com.shotcutter.movies.movie;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class MovieDTO {
    private Long id;
    private Boolean adult;
    private Long voteCount;
    private Double voteAverage;
    private Long popularity;
    private Set<String> genres;
    private Date releaseDate;
    private String posterPath;
    private String overview;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdropPath;
}
