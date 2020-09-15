package com.shotcutter.rest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.SpringApplication;

@EnableAsync
@EnableScheduling
@SpringBootApplication
@ComponentScan("com.shotcutter.library")
@ComponentScan("com.shotcutter.rest")
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

}
