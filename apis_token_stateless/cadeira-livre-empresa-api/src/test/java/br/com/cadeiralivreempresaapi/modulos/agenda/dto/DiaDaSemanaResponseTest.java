package br.com.cadeiralivreempresaapi.modulos.agenda.dto;

import br.com.cadeiralivreempresaapi.modulos.agenda.dto.horario.DiaDaSemanaResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.cadeiralivreempresaapi.modulos.agenda.mocks.HorarioMocks.umDiaDaSemana;
import static org.assertj.core.api.Assertions.assertThat;

public class DiaDaSemanaResponseTest {

    @Test
    @DisplayName("Deve converter para DTO de DiaDaSemanaResponse quando informar Model de DiaDaSemana")
    public void of_deveConverterParaDtoDiaDaSemanaResponse_quandoInformarModelDeDiaDaSemana() {
        var response = DiaDaSemanaResponse.of(umDiaDaSemana());
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getDiaNome()).isEqualTo("Segunda-feira");
        assertThat(response.getDia()).isEqualTo(0);
    }
}
