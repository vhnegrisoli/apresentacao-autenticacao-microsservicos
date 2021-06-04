package br.com.biot.integracaopagarmeapi.config;

import br.com.biot.integracaopagarmeapi.modulos.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.com.biot.integracaopagarmeapi.modulos.util.TokenUtil.extrairTokenDoRequest;

public class AuthorizationTokenInterceptor implements HandlerInterceptor {

    private static final String ENDPOINT_PROTEGIDO = "/api/";
    private static final String OPTIONS_METHOD = "OPTIONS";

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        if (possuiUrlQueNecessitaAutenticacao(request)) {
            return isOptions(request) || possuiAuthorizationHeaderComTokenValido(request);
        }
        return true;
    }

    private boolean possuiUrlQueNecessitaAutenticacao(HttpServletRequest request) {
        return request.getRequestURI().contains(ENDPOINT_PROTEGIDO);
    }

    private boolean possuiAuthorizationHeaderComTokenValido(HttpServletRequest request) {
        var token = extrairTokenDoRequest(request);
        return jwtService.possuiUsuarioAutenticado(token);
    }

    private boolean isOptions(HttpServletRequest request) {
        return request.getMethod().equals(OPTIONS_METHOD);
    }
}
