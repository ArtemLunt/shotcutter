package com.shotcutter.sso.jwt;

import com.shotcutter.library.jwt.JWTService;
import com.shotcutter.sso.shared.constant.EnvironmentVariable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfig {

    @Value(EnvironmentVariable.ENCRYPTION_SECRET)
    private String secret;

    @Bean
    JWTService jwtService() {
        return new JWTService(secret);
    }

}
