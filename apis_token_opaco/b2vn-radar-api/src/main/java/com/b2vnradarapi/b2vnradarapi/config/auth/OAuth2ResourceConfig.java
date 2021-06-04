package com.b2vnradarapi.b2vnradarapi.config.auth;

import com.b2vnradarapi.b2vnradarapi.config.CorsConfigFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import static com.b2vnradarapi.b2vnradarapi.config.auth.EPermissao.ADMIN;
import static com.b2vnradarapi.b2vnradarapi.config.auth.EPermissao.USER;

@Configuration
@EnableResourceServer
public class OAuth2ResourceConfig extends ResourceServerConfigurerAdapter {

    @Value("${app-config.oauth-clients.b2vn-radar-api.client}")
    private String oauthClient;
    @Value("${app-config.oauth-clients.b2vn-radar-api.secret}")
    private String oauthClientSecret;
    @Value("${app-config.services.b2vn-auth-api.url}")
    private String oauthServerUrl;

    @Override
    @SuppressWarnings({"checkstyle:methodlength"})
    public void configure(HttpSecurity http) throws Exception {
        String[] permitAll = {
            "/",
            "/login/**",
            "/oauth/token",
            "/oauth/authorize",
            "/api/usuarios/novo",
            "/api/clientes/endereco/**",
            "/swagger-ui.html",
            "/swagger-ui.html**",
            "/swagger-resources/**",
            "/v2/api-docs**",
            "/webjars/**",
            "/api/docs",
        };

        http
            .addFilterBefore(new CorsConfigFilter(), ChannelProcessingFilter.class)
            .requestMatchers()
            .antMatchers("/**")
            .and()
            .authorizeRequests()
            .antMatchers(permitAll).permitAll()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers("/api/radares/**").hasAnyRole(ADMIN.name(), USER.name())
            .antMatchers("/api/relatorios/**").hasAnyRole(ADMIN.name(), USER.name())
            .antMatchers("/api/trajetos/**").hasAnyRole(ADMIN.name(), USER.name());
    }

    @Override
    public void configure(final ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices());
    }

    @Primary
    @Bean
    public RemoteTokenServices tokenServices() {
        final RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl(oauthServerUrl + "/oauth/check_token");
        tokenService.setClientId(oauthClient);
        tokenService.setClientSecret(oauthClientSecret);
        return tokenService;
    }
}
