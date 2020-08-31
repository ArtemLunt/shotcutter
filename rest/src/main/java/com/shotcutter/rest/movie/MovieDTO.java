package com.shotcutter.rest.movie;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MovieDTO {
    private Long id;
    private Boolean adult;
    private Integer voteCount;
    private Integer voteAverage;
    private Integer popularity;
    private List<String> genres;
    private Date releaseDate;
    private String posterPath;
    private String overview;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdropPath;
}
