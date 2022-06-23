package com.shotcutter.movies.TMDB;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;

@Data
@Service
@ConditionalOnProperty(value = "tmdb.integration.init")
public class TMDBGenresService {

    private Map<Integer, String> genresMap;

}
