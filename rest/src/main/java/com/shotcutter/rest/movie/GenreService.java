package com.shotcutter.rest.movie;

import org.springframework.stereotype.Service;
import lombok.Data;

import java.util.Map;

@Data
@Service
public class GenreService {
    private Map<Integer, String> genresMap;
}
