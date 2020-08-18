package com.shotcutter.rest.movie;

import org.springframework.stereotype.Service;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Service
public class GenreService {
    @Getter @Setter private Map<Integer, String> genresMap;
}
