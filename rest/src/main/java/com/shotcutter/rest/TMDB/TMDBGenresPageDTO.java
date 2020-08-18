package com.shotcutter.rest.TMDB;

import lombok.Data;

import java.util.List;

@Data
public class TMDBGenresPageDTO {
    private List<TMDBGenreDTO> genres;
}
