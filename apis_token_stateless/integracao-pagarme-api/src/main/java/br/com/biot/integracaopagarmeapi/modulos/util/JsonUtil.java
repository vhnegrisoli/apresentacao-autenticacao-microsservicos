package br.com.biot.integracaopagarmeapi.modulos.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    private static final String EMPTY_JSON_OBJECT = "{}";

    public static String converterJsonParaString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception ex) {
            log.error("Erro ao tentar converter objeto para JSON String: ", ex);
            return EMPTY_JSON_OBJECT;
        }
    }
}
