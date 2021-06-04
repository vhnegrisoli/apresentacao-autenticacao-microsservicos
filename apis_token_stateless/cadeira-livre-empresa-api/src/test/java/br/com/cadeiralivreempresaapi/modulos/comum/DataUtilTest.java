package br.com.cadeiralivreempresaapi.modulos.comum;

import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import static br.com.cadeiralivreempresaapi.modulos.comum.util.DataUtil.converterParaLocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class DataUtilTest {

    @Test
    @SneakyThrows
    @DisplayName("Deve converter data no formato Date para o formato LocalDateTime")
    public void converterParaLocalDateTime_deveConverterParaLocalDateTime_quandoInformarDate() {
        var localDateTime = converterParaLocalDateTime(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
        assertThat(localDateTime).isNotNull();
        assertThat(localDateTime).isEqualTo(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        assertThat(localDateTime).isEqualTo(LocalDateTime.parse("2020-01-01T00:00:00"));
    }

    @Test
    @DisplayName("Deve lançar exception ao informar formato de data incorreto.")
    public void converterParaLocalDateTime_deveLancarException_quandoInformarFormatoDeDataErrado() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> converterParaLocalDateTime(null))
            .withMessage("Formato de data inválido.");
    }
}
