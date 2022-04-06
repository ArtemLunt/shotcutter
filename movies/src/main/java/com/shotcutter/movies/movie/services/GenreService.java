package com.shotcutter.movies.movie.services;

import org.springframework.stereotype.Service;
import lombok.Data;

import java.util.Map;

@Data
@Service
public class GenreService {
    private Map<Integer, String> genresMap;
}
