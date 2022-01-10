package com.shotcutter.movies.movie.entities.search;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@Document(indexName = "movies", createIndex = true)
public class MovieSearchEntity {
    @Id
    private Long id;
    @Field(type = FieldType.Boolean)
    private Boolean adult;
    private Long popularity;
    @Field(type = FieldType.Text)
    private Set<String> genres;
    @Field(type = FieldType.Date)
    private Date releaseDate;
    private String posterPath;
    @Field(type = FieldType.Text)
    private String overview;
    @Field(type = FieldType.Keyword)
    private String originalTitle;
    private String originalLanguage;
    @Field(type = FieldType.Keyword)
    private String title;
}
