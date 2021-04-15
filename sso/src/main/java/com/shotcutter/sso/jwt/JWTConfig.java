package com.shotcutter.sso.jwt;

import com.shotcutter.library.jwt.JWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JWTConfig {

    @Value("${shotcutter.encryption.private}")
    private String secret;

    @Bean
    public JWTService jwtService() {
        return new JWTService(secret);
    }

}
