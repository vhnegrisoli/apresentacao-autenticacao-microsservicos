package br.com.cadeiralivreempresaapi.modulos.comum.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumeroUtil {

    private static final Integer UMA_CASA_DECIMAL = 1;
    private static final Integer DUAS_CASAS_DECIMAIS = 2;

    public static BigDecimal converterParaUmaCasaDecimal(Double numero) {
        return BigDecimal
            .valueOf(numero)
            .setScale(UMA_CASA_DECIMAL, RoundingMode.HALF_UP);
    }

    public static BigDecimal converterParaUmaCasaDecimal(BigDecimal numero) {
        return numero.setScale(UMA_CASA_DECIMAL, RoundingMode.HALF_UP);
    }

    public static BigDecimal converterParaDuasCasasDecimais(Double numero) {
        return BigDecimal
            .valueOf(numero)
            .setScale(DUAS_CASAS_DECIMAIS, RoundingMode.HALF_UP);
    }

    public static BigDecimal converterParaDuasCasasDecimais(Long numero) {
        return BigDecimal
            .valueOf(numero)
            .setScale(DUAS_CASAS_DECIMAIS, RoundingMode.HALF_UP);
    }

    public static BigDecimal converterParaDuasCasasDecimais(BigDecimal numero) {
        return numero.setScale(DUAS_CASAS_DECIMAIS, RoundingMode.HALF_UP);
    }
}
