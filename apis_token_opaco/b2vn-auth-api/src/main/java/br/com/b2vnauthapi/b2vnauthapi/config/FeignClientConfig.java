package br.com.b2vnauthapi.b2vnauthapi.config;

import br.com.b2vnauthapi.b2vnauthapi.config.auth.ClientCredentialsTokenService;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
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

        if (getCurrentRequestUri().contains("/api/auth/token")) {
            return Strings.EMPTY;
        }

        return hasUsuarioAutenticado(authentication)
            ? ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue()
            : tokenService.getToken().getValue();
    }

    private boolean hasUsuarioAutenticado(Authentication authentication) {
        return authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails;
    }

    private String getCurrentRequestUri() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes())
                .getRequest()
                .getRequestURI();
        } catch (Exception ex) {
            log.error("Falha ao tentar recuperar o request atual: ", ex);
            return Strings.EMPTY;
        }
    }
}
