package com.shotcutter.movies.TMDB;

public enum PathVariable {
    PAGE("page"),
    API_KEY("api_key");

    private String value;

    PathVariable(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
