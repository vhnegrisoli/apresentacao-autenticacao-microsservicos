package br.com.biot.integracaopagarmeapi.modulos.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class NumeroUtil {

    private static final Integer UMA_CASA_DECIMAL = 1;
    private static final Integer DUAS_CASAS_DECIMAIS = 2;
    private static final String PONTO_DECIMAL = ".";
    private static final String PREFIXO_DUAS_CASAS = ".00";
    private static final Integer INICIO_NUMERO = 0;

    public static BigDecimal converterParaDuasCasasDecimais(Double numero) {
        return BigDecimal
            .valueOf(numero)
            .setScale(DUAS_CASAS_DECIMAIS, RoundingMode.HALF_UP);
    }

    public static BigDecimal tratarValorPagamento(BigDecimal valorPagamento) {
        try {
            var valorPagamentoString = tratarStringNumericaInicial(valorPagamento.toString());
            var tamanhoNumero = valorPagamentoString.length();
            var tamanhoInteiro = tamanhoNumero - DUAS_CASAS_DECIMAIS;
            var valorDecimal = valorPagamentoString.substring(tamanhoInteiro);
            var valorInteiro = valorPagamentoString.substring(INICIO_NUMERO, tamanhoInteiro);
            var numeroCompletoComDuasCasasDecimais = valorInteiro.concat(PONTO_DECIMAL).concat(valorDecimal);
            return converterParaDuasCasasDecimais(Double.parseDouble(numeroCompletoComDuasCasasDecimais));
        } catch (Exception ex) {
            log.error("Erro ao tentar converter o número para duas casas dentro do número.");
            return BigDecimal.ZERO;
        }
    }

    private static String tratarStringNumericaInicial(String valor) {
        if (valor.contains(PREFIXO_DUAS_CASAS)) {
            valor = valor.replace(PREFIXO_DUAS_CASAS, Strings.EMPTY);
        }
        if (valor.length() == UMA_CASA_DECIMAL) {
            valor = valor.concat(String.valueOf(INICIO_NUMERO));
        }
        return valor;
    }
}
