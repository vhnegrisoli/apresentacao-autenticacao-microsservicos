package br.com.cadeiralivreempresaapi.modulos.agenda.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static br.com.cadeiralivreempresaapi.modulos.agenda.mocks.HorarioMocks.umHorarioRequest;
import static org.assertj.core.api.Assertions.assertThat;

public class HorarioTest {

    @Test
    @DisplayName("Deve converter para Model de Horario quando informar DTO de HorarioRequest")
    public void of_deveConverterParaModelDeHorario_quandoInformarDtoDeHorarioRequest() {
        var horario = Horario.of(umHorarioRequest());
        assertThat(horario).isNotNull();
        assertThat(horario.getDiaDaSemana().getId()).isEqualTo(1);
        assertThat(horario.getEmpresa().getId()).isEqualTo(1);
        assertThat(horario.getHorario()).isEqualTo(LocalTime.of(12, 0));
    }
}
