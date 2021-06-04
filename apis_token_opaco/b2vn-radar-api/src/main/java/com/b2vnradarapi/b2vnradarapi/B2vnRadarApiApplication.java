package com.b2vnradarapi.b2vnradarapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableFeignClients
@EnableWebMvc
public class B2vnRadarApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(B2vnRadarApiApplication.class, args);
    }
}
