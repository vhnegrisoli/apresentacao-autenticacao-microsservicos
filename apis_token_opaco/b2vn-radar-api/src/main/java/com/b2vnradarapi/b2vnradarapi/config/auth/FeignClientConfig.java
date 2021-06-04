package com.b2vnradarapi.b2vnradarapi.config.auth;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

@Configuration
public class FeignClientConfig {

    @Autowired
    private ClientCredentialsTokenService tokenService;

    @Bean
    public RequestInterceptor requestTokenBearerInterceptor() {
        return requestTemplate -> requestTemplate.header("Authorization", "Bearer " + getToken());
    }

    private String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return hasUsuarioAutenticado(authentication)
            ? ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue()
            : tokenService.getToken().getValue();
    }

    private boolean hasUsuarioAutenticado(Authentication authentication) {
        return authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails;
    }
}
