package com.b2vnradarapi.b2vnradarapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@Controller
public class MainControllerConfig {

    @Value("${app-config.application.swagger-url}")
    private String swaggerUrl;

    @GetMapping
    public ResponseEntity<Object> redirecionarParaSwagger() throws Exception {
        var swaggerUri = new URI(swaggerUrl);
        var headers = new HttpHeaders();
        headers.setLocation(swaggerUri);
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }
}
