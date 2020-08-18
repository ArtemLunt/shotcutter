package com.shotcutter.rest.TMDB;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TMDBGenresPageDTO {
    @Getter @Setter private List<TMDBGenreDTO> genres;
}
