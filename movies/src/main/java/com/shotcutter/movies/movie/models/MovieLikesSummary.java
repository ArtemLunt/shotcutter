package com.shotcutter.movies.movie.models;

import com.shotcutter.movies.movie.entities.db.MovieLikeDBEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieLikesSummary {
    private final Long relatedMovieId;
    private final MovieLikeDBEntity likeByCurrentUser;
    private final Long amountOfLikes;
    private final Long amountOfDislikes;
}
