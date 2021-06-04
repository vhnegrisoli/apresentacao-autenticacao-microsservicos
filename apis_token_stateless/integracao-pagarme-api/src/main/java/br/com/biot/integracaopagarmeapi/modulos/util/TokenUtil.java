package br.com.biot.integracaopagarmeapi.modulos.util;

import br.com.biot.integracaopagarmeapi.config.exception.AutenticacaoException;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.util.ObjectUtils.isEmpty;

public class TokenUtil {

    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String BEARER = "bearer";
    private static final String EMPTY_SPACE = " ";
    private static final String EMPTY = "";
    private static final Integer TOKEN_INDEX = 1;
    private static final Integer TOKEN_INITIAL_INDEX = 0;
    private static final Integer TOKEN_BEARER_INDEX = 7;

    public static String extrairTokenDoRequest(HttpServletRequest request) {
        var accessToken = request.getHeader(AUTHORIZATION_HEADER);
        if (isEmpty(accessToken)) {
            throw new AutenticacaoException("Token de acesso não informado.");
        }
        if (accessToken.toLowerCase().contains(BEARER) && accessToken.contains(EMPTY_SPACE)) {
                return accessToken.split(EMPTY_SPACE)[TOKEN_INDEX];
        }
        if (accessToken.toLowerCase().contains(BEARER) && !accessToken.contains(EMPTY_SPACE)) {
            return accessToken.substring(TOKEN_INITIAL_INDEX, TOKEN_BEARER_INDEX);
        }
        return accessToken;
    }
}
