package br.com.cadeiralivreempresaapi.modulos.transacao.util;

import java.math.BigDecimal;

import static br.com.cadeiralivreempresaapi.modulos.comum.util.NumeroUtil.converterParaDuasCasasDecimais;

public class PrecoUtil {

    private static final String PONTO = ".";
    private static final String VIRGULA = ",";
    private static final String VAZIO = "";

    public static BigDecimal tratarValorTransacao(Double valor) {
        try {
            var valorDuasCasas = converterParaDuasCasasDecimais(valor);
            var valorDuasCasasString = valorDuasCasas.toString();
            valorDuasCasasString = valorDuasCasasString.replace(PONTO, VAZIO);
            valorDuasCasasString = valorDuasCasasString.replace(VIRGULA, VAZIO);
            return converterParaDuasCasasDecimais(Long.parseLong(valorDuasCasasString));
        } catch (Exception ex) {
            return BigDecimal.ZERO;
        }
    }
}
