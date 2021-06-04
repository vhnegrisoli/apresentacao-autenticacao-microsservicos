package br.com.cadeiralivreempresaapi.modulos.agenda.dto;

import br.com.cadeiralivreempresaapi.modulos.agenda.dto.horario.HorarioResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static br.com.cadeiralivreempresaapi.modulos.agenda.mocks.HorarioMocks.umHorario;
import static org.assertj.core.api.Assertions.assertThat;

public class HorarioResponseTest {

    @Test
    @DisplayName("Deve converter para DTO de HorarioResponse quando informar Model de Horario")
    public void of_deveConverterParaDtoHorarioResponse_quandoInformarModelDeHorario() {
        var response = HorarioResponse.of(umHorario());
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getHorario()).isEqualTo(LocalTime.of(12, 0));
        assertThat(response.getDiaDaSemanaId()).isEqualTo(1);
        assertThat(response.getDiaNome()).isEqualTo("Segunda-feira");
        assertThat(response.getDiaNumerico()).isEqualTo(0);
    }
}
