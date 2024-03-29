package com.shotcutter.sso.security;

public enum SecurityRequestParam {

    AUTH_STATUS("status"),
    REDIRECT_URL("redirect_uri"),
    ACCESS_TOKEN("access_token");

    private final String title;

    SecurityRequestParam(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return this.title;
    }

}
