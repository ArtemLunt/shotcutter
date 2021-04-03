package com.shotcutter.securitystarter.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //private AuthenticationFilterRr authenticationFilter;
    private JWTAuthManager authManager;
    private JWTAuthConverter authConverter;

    public SecurityConfig(JWTAuthManager authManager, JWTAuthConverter authConverter) {
        this.authManager = authManager;
        this.authConverter = authConverter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .addFilterAt(new AuthenticationFilter(authManager, authConverter),
                             UsernamePasswordAuthenticationFilter.class)
                .formLogin().successHandler(new SimpleUrlAuthenticationSuccessHandler())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    }

}
