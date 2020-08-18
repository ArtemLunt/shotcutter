package com.shotcutter.rest.TMDB;

public enum Endpoint {
    GENRES("/genre/movie/list"),
    POPULAR_MOVIES("/movie/popular");

    private String value;

    Endpoint(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
