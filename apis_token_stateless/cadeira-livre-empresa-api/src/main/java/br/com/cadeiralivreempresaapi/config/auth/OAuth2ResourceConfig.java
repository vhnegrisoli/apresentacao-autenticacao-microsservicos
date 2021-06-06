package br.com.cadeiralivreempresaapi.config.auth;

import br.com.cadeiralivreempresaapi.modulos.usuario.enums.EPermissao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import static java.util.Arrays.asList;

@Configuration
@EnableResourceServer
public class OAuth2ResourceConfig extends ResourceServerConfigurerAdapter {

    private static final String TEST_PROFILE = "test";

    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private Environment environment;

    @Override
    @SuppressWarnings({"checkstyle:methodlength"})
    public void configure(HttpSecurity http) throws Exception {
        String[] permitAll = {
            "/",
            "/login/**",
            "/oauth/token",
            "/oauth/authorize",
            "/api/usuarios/proprietario",
            "/api/usuarios/is-authenticated",
            "/swagger-ui.html**",
            "/swagger-resources/**",
            "/v2/api-docs**",
            "/webjars/**",
            "/api/docs",
            "/api/jwt/**",
            "**cliente-api**",
            "/api/empresas/cliente-api",
            "/api/empresas/{id}/cliente-api",
            "/api/localidade/**",
            "/api/auth/token"
        };

        http
            .addFilterBefore(new CorsConfigFilter(), ChannelProcessingFilter.class)
            .requestMatchers()
            .antMatchers("/**")
            .and()
            .authorizeRequests()
            .antMatchers(permitAll).permitAll()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers("/api/usuarios/usuario-autenticado").authenticated()
            .antMatchers("/api/usuarios/get-token").authenticated()
            .antMatchers("/api/usuarios/is-authenticated").authenticated()
            .antMatchers("/api/usuarios/atualizar-token-notificacao").authenticated()
            .antMatchers("/api/notificacoes/usuario/**").hasRole(EPermissao.ADMIN.name())
            .antMatchers("/api/usuarios/**")
            .hasAnyRole(EPermissao.ADMIN.name(), EPermissao.PROPRIETARIO.name(), EPermissao.SOCIO.name())
            .antMatchers("/api/empresas/**")
            .hasAnyRole(EPermissao.ADMIN.name(), EPermissao.PROPRIETARIO.name(), EPermissao.SOCIO.name())
            .antMatchers(HttpMethod.POST, "/api/horarios/**")
            .hasAnyRole(EPermissao.ADMIN.name(), EPermissao.PROPRIETARIO.name(), EPermissao.SOCIO.name())
            .antMatchers(HttpMethod.PUT, "/api/horarios/**")
            .hasAnyRole(EPermissao.ADMIN.name(), EPermissao.PROPRIETARIO.name(), EPermissao.SOCIO.name())
            .antMatchers(HttpMethod.DELETE, "/api/horarios/**")
            .hasAnyRole(EPermissao.ADMIN.name(), EPermissao.PROPRIETARIO.name(), EPermissao.SOCIO.name())
            .antMatchers(HttpMethod.GET, "/api/horarios/**")
            .hasAnyRole(EPermissao.ADMIN.name(), EPermissao.PROPRIETARIO.name(), EPermissao.SOCIO.name(),
                EPermissao.FUNCIONARIO.name());
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        if (asList(environment.getActiveProfiles()).contains(TEST_PROFILE)) {
            resources.stateless(false);
        }
        resources.tokenStore(tokenStore);
    }
}
