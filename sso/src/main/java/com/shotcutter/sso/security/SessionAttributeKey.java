package com.shotcutter.sso.security;

public enum SessionAttributeKey {

    REDIRECT_URL("redirectUrl");

    private final String title;

    SessionAttributeKey(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

}
