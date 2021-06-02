package com.shotcutter.securitystarter.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthManager authManager;
    private final JWTAuthConverter authConverter;
    private final RedirectStrategy redirectStrategy;

    public SecurityConfig(JWTAuthManager authManager,
                          JWTAuthConverter authConverter,
                          RedirectStrategy redirectStrategy) {
        this.authManager = authManager;
        this.authConverter = authConverter;
        this.redirectStrategy = redirectStrategy;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(authenticationFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
    }

    private AuthenticationFilter authenticationFilter() {
        var filter = new AuthenticationFilter(authManager, authConverter);
        filter.setSuccessHandler(authSuccessHandler());

        return filter;
    }

    private AuthenticationSuccessHandler authSuccessHandler() {
        var successHandler = new SimpleUrlAuthenticationSuccessHandler();
        successHandler.setRedirectStrategy(redirectStrategy);

        return successHandler;
    }

}
