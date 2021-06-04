package br.com.cadeiralivreempresaapi.modulos.comum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static br.com.cadeiralivreempresaapi.modulos.comum.util.NumeroUtil.converterParaDuasCasasDecimais;
import static br.com.cadeiralivreempresaapi.modulos.comum.util.NumeroUtil.converterParaUmaCasaDecimal;
import static org.assertj.core.api.Assertions.assertThat;

public class NumeroUtilTest {

    @Test
    @DisplayName("Deve converter para uma casa decimal ao informar número")
    public void converterParaUmaCasaDecimal_deveConverterParaUmaCasa_quandoEnviarNumero() {
        assertThat(converterParaUmaCasaDecimal(25.56).doubleValue()).isEqualTo(25.6);
        assertThat(converterParaUmaCasaDecimal(BigDecimal.valueOf(25.56)).doubleValue()).isEqualTo(25.6);
    }

    @Test
    @DisplayName("Deve converter para duas casas decimais ao informar número")
    public void converterParaDuasCasasDecimais_deveConverterParaDuasCasas_quandoEnviarNumero() {
        assertThat(converterParaDuasCasasDecimais(25.568).doubleValue()).isEqualTo(25.57);
        assertThat(converterParaDuasCasasDecimais(BigDecimal.valueOf(25.568)).doubleValue()).isEqualTo(25.57);
    }
}
