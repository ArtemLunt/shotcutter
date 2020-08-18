package com.shotcutter.rest.TMDB;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
public class TMDBMovieDTO {
    @Getter @Setter private Long id;
    @Getter @Setter private Boolean adult;
    @Getter @Setter private String overview;
    @Getter @Setter private String title;
    @Getter @Setter private Long popularity;
    @Getter @Setter private String video;
    @Getter @Setter private String poster_path;
    @Getter @Setter private Date release_date;
    @Getter @Setter private Set<Long> genre_ids;
    @Getter @Setter private String original_title;
    @Getter @Setter private String original_language;
    @Getter @Setter private String backdrop_path;
    @Getter @Setter private Long vote_count;
    @Getter @Setter private Double vote_average;
}
