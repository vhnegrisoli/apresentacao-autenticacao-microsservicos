package br.com.biot.integracaopagarmeapi.modulos.util;

import br.com.biot.integracaopagarmeapi.config.exception.ValidacaoException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static HttpServletRequest getCurrentRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes())
                .getRequest();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ValidacaoException("Erro ao tentar recuperar o request atual.");
        }
    }
}
