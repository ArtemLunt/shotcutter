package com.shotcutter.movies.TMDB;

import lombok.Data;

import java.util.List;

@Data
public class TMDBGenresPageDTO {
    private List<TMDBGenreDTO> genres;
}
