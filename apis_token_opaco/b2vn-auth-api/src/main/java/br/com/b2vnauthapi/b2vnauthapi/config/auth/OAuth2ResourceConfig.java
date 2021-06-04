package br.com.b2vnauthapi.b2vnauthapi.config.auth;

import br.com.b2vnauthapi.b2vnauthapi.config.CorsConfigFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import static br.com.b2vnauthapi.b2vnauthapi.modules.usuario.enums.EPermissao.ADMIN;
import static br.com.b2vnauthapi.b2vnauthapi.modules.usuario.enums.EPermissao.USER;

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
            "/api/usuarios/novo"
        };

        http
            .addFilterBefore(new CorsConfigFilter(), ChannelProcessingFilter.class)
            .requestMatchers()
            .antMatchers("/**")
            .and()
            .authorizeRequests()
            .antMatchers(permitAll).permitAll()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers("/api/log/**").hasAnyRole(ADMIN.name(), USER.name())
            .antMatchers("/api/usuarios/admin/novo").hasRole(ADMIN.name())
            .antMatchers("/api/usuarios/**").hasAnyRole(ADMIN.name(), USER.name());
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
