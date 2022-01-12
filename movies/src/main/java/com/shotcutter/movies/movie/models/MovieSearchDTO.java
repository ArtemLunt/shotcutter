package com.shotcutter.movies.movie.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class MovieSearchDTO {
    private Long id;
    private Boolean adult;
    private Long popularity;
    private Set<String> genres;
    private Date releaseDate;
    private String posterPath;
    private String overview;
    private String originalTitle;
    private String originalLanguage;
    private String title;
}
