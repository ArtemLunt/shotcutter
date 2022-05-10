package com.shotcutter.movies.movie.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieLikesSummaryDTO {
    private final Long relatedMovieId;
    private final Boolean likedByCurrentUser;
    private final Boolean dislikedByCurrentUser;
    private final Long amountOfLikes;
    private final Long amountOfDislikes;
}
