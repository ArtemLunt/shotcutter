package com.shotcutter.movies.movie.models;

import com.shotcutter.movies.movie.entities.db.MovieLikeDBEntity;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@With
@Builder
public class MovieLikesSummary {
    private final Long relatedMovieId;
    private final Boolean likedByCurrentUser;
    private final Boolean dislikedByCurrentUser;
    private final Long amountOfLikes;
    private final Long amountOfDislikes;
}
