package br.com.biot.integracaopagarmeapi.modulos.jwt.service;

import br.com.biot.integracaopagarmeapi.config.exception.AutenticacaoException;
import br.com.biot.integracaopagarmeapi.modulos.jwt.dto.JwtUsuarioResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static br.com.biot.integracaopagarmeapi.modulos.util.RequestUtil.getCurrentRequest;
import static br.com.biot.integracaopagarmeapi.modulos.util.TokenUtil.extrairTokenDoRequest;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class JwtService {

    private static final AutenticacaoException TOKEN_ACESSO_INVALIDO
        = new AutenticacaoException("Token de acesso inválido.");

    @Value("${jwt.secret}")
    private String secret;

    public boolean possuiUsuarioAutenticado(String token) {
        var usuario = recuperarDadosDoUsuarioDoToken(token);
        return !isEmpty(usuario);
    }

    public JwtUsuarioResponse recuperarUsuarioAutenticado() {
        var token = extrairTokenDoRequest(getCurrentRequest());
        return recuperarDadosDoUsuarioDoToken(token);
    }

    public JwtUsuarioResponse recuperarDadosDoUsuarioDoToken(String jwt) {
        try {
            return JwtUsuarioResponse.of(descriptografarJwt(jwt).getBody());
        } catch (Exception ex) {
            log.error("Erro ao processar token de acesso: ".concat(jwt).concat(". Erro: "), ex);
            throw TOKEN_ACESSO_INVALIDO;
        }
    }

    private Jws<Claims> descriptografarJwt(String jwt) {
        try {
            return Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(jwt);
        } catch (Exception ex) {
            log.error("Erro ao processar token de acesso: ".concat(jwt).concat(". Erro: "), ex);
            throw TOKEN_ACESSO_INVALIDO;
        }
    }
}
