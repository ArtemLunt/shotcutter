package com.shotcutter.movies.movie.entities.db;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@With
@Builder
@Document(collection = "likes")
public class MovieLikeDBEntity {
    @Id
    private final String id;
    private final String relatedUserId;
    private final Long relatedMovieId;
    private final Boolean value;
    private final Date updateTime;
}
