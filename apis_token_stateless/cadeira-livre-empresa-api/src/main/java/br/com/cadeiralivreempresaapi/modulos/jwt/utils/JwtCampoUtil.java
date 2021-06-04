package br.com.cadeiralivreempresaapi.modulos.jwt.utils;

import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

import static br.com.cadeiralivreempresaapi.modulos.comum.util.Constantes.ID;

@Slf4j
public class JwtCampoUtil {

    public static String getCampoId(Claims claims) {
        try {
            return (String) claims.get(ID);
        } catch (Exception ex) {
            log.error("Erro ao tentar recuperar ID do JWT: {}", claims.toString());
            throw new ValidacaoException("Erro ao tentar recuperar o campo ID do Token.");
        }
    }

    public static String getCampo(String campo, Claims claims) {
        try {
            return (String) claims.get(campo);
        } catch (Exception ex) {
            log.error("Erro ao tentar recuperar o campo {} do JWT: {}", campo, claims.toString());
            throw new ValidacaoException(
                String.format("Erro ao tentar recuperar o campo %s do Token.", campo)
            );
        }
    }
}
