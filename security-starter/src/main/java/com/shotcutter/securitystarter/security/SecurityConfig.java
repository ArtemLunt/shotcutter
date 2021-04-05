package com.shotcutter.securitystarter.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JWTAuthManager authManager;
    private JWTAuthConverter authConverter;

    public SecurityConfig(JWTAuthManager authManager, JWTAuthConverter authConverter) {
        this.authManager = authManager;
        this.authConverter = authConverter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(authenticationFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().authenticated();
    }

    private AuthenticationFilter authenticationFilter() {
        var filter = new AuthenticationFilter(authManager, authConverter);
        filter.setSuccessHandler(authSuccessHandler());

        return filter;
    }

    private AuthenticationSuccessHandler authSuccessHandler() {
        var successHandler = new SimpleUrlAuthenticationSuccessHandler();
        successHandler.setRedirectStrategy(new RedirectStrategyImpl.NoRedirectStrategy());

        return successHandler;
    }

}
