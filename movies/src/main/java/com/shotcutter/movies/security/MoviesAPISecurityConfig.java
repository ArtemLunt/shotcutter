package com.shotcutter.movies.security;


import com.shotcutter.securitystarter.security.JWTAuthConverter;
import com.shotcutter.securitystarter.security.JWTAuthManager;
import com.shotcutter.securitystarter.security.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.RedirectStrategy;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@EnableWebSecurity
public class MoviesAPISecurityConfig extends SecurityConfig {
    public MoviesAPISecurityConfig(JWTAuthManager authManager,
                                  JWTAuthConverter authConverter,
                                  RedirectStrategy redirectStrategy) {
        super(authManager, authConverter, redirectStrategy);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/movies/**", "/api/likes/summary/**", "/api/genres")
                .permitAll();
        super.configure(http);
    }
}
