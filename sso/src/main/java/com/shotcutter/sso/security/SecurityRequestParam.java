package com.shotcutter.sso.security;

public enum SecurityRequestParam {

    REDIRECT_TO("redirectTo");

    private final String title;

    SecurityRequestParam(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return this.title;
    }

}
