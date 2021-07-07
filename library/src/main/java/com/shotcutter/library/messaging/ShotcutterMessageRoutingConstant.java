package com.shotcutter.library.messaging;

public class ShotcutterMessageRoutingConstant {
    public final static class User {
        public final static String EXCHANGE_NAME = "com.shotcutter.identity";
        public final static String FIND_BY_ID = "by-id";
        public final static String FIND_BY_EMAIL = "by-email";
        public final static String REGISTRATION = "registration";
    }

    public final static class Authentication {
        public final static String EXCHANGE_NAME = "com.shotcutter.sso";
        public final static String GET_ID_BY_TOKEN = "user-id-by-token";
    }
}
