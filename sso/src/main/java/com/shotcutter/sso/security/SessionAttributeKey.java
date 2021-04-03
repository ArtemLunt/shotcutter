package com.shotcutter.sso.security;

public enum SessionAttributeKey {

    REDIRECT_URL("redirect_uri");

    private final String title;

    SessionAttributeKey(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

}
