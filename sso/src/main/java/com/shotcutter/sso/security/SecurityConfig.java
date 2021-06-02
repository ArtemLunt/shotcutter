package com.shotcutter.sso.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final RedirectUrlFilter redirectUrlFilter;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    public SecurityConfig(RedirectUrlFilter redirectUrlFilter,
                          AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.redirectUrlFilter = redirectUrlFilter;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(redirectUrlFilter, OAuth2LoginAuthenticationFilter.class)
                .antMatcher("/**")
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .successHandler(authenticationSuccessHandler);
    }

}
