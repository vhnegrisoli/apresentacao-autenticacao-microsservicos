package br.com.cadeiralivreempresaapi.modulos.agenda.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.cadeiralivreempresaapi.modulos.agenda.mocks.ServicoMocks.umServicoRequest;
import static org.assertj.core.api.Assertions.assertThat;

public class ServicoTest {

    @Test
    @DisplayName("Deve converter para Model de Servico quando informar DTO de ServicoRequest")
    public void of_deveConverterParaModelDeServico_quandoInformarDtoDeServicoRequest() {
        var servico = Servico.of(umServicoRequest());
        assertThat(servico).isNotNull();
        assertThat(servico.getDescricao()).isEqualTo("Corte de cabelo");
        assertThat(servico.getEmpresa().getId()).isEqualTo(1);
        assertThat(servico.getPreco()).isEqualTo(25.00);
    }
}
