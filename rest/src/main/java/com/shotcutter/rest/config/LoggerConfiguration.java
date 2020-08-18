package com.shotcutter.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import com.shotcutter.rest.RestApplication;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Configuration
public class LoggerConfiguration {
    @Bean
    Logger logger() {
        return LoggerFactory.getLogger(RestApplication.class);
    }
}
