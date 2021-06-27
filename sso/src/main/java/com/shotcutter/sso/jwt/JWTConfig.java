package com.shotcutter.sso.jwt;

import com.shotcutter.library.jwt.JWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JWTConfig {

    private final String secret;

    public JWTConfig(@Value("${shotcutter.encryption.private}") String secret) {
        this.secret = secret;
    }

    @Bean
    public JWTService jwtService() {
        return new JWTService(secret);
    }

}
