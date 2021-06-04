package br.com.cadeiralivreempresaapi.modulos.comum.util;

import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class DataUtil {

    public static LocalDateTime converterParaLocalDateTime(Date data) {
        try {
            return data
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        } catch (Exception ex) {
            log.error("Formato de data inválido.", ex);
            throw new ValidacaoException("Formato de data inválido.");
        }
    }
}