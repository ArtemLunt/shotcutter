package com.shotcutter.rest.TMDB;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
public class TMDBMovieDTO {
    private Long id;
    private Boolean adult;
    private String overview;
    private String title;
    private Long popularity;
    private String video;
    private String poster_path;
    private Date release_date;
    private Set<Long> genre_ids;
    private String original_title;
    private String original_language;
    private String backdrop_path;
    private Long vote_count;
    private Double vote_average;
}
