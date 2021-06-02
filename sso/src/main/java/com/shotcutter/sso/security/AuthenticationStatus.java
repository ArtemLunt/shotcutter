package com.shotcutter.sso.security;

public enum AuthenticationStatus {
    FAILURE("failure"),
    SUCCESS("success");

    private String value;

    AuthenticationStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
