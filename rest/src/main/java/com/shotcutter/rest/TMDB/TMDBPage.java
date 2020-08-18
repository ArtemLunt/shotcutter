package com.shotcutter.rest.TMDB;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TMDBPage<T> {
    @Getter @Setter private Integer page;
    @Getter @Setter private List<T> results;

    private Integer total_results;
    private Integer total_pages;

    public Integer getTotalResults() {
        return total_results;
    }

    public void setTotalResults(Integer total_results) {
        this.total_results = total_results;
    }

    public Integer getTotalPages() {
        return total_pages;
    }

    public void setTotalPages(Integer total_pages) {
        this.total_pages = total_pages;
    }
}
