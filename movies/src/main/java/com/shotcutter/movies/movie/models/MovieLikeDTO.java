package com.shotcutter.movies.movie.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MovieLikeDTO {
    private final String id;
    private final String relatedUserId;
    private final Long relatedMovieId;
    private final Boolean value;
    private final Date updateTime;
}
