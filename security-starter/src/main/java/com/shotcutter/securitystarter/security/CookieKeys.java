package com.shotcutter.securitystarter.security;

public enum CookieKeys {

    ACCESS_TOKEN("access_token");

    private String value;

    private CookieKeys(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
