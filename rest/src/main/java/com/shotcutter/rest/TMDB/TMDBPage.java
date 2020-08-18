package com.shotcutter.rest.TMDB;

import lombok.Data;

import java.util.List;

@Data
public class TMDBPage<T> {
    private Integer page;
    private List<T> results;

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
