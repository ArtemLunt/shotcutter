package com.shotcutter.movies.movie.models;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class MovieLookupDTO {
    private Long id;
    private Set<String> genres;
    private String posterPath;
    private String overview;
    private String title;
}
