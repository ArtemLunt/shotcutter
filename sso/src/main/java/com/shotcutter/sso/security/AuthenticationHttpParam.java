package com.shotcutter.sso.security;

public enum AuthenticationHttpParam {

    ACCESS_TOKEN("access_token");

    private final String title;

    AuthenticationHttpParam(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return this.title;
    }

}
