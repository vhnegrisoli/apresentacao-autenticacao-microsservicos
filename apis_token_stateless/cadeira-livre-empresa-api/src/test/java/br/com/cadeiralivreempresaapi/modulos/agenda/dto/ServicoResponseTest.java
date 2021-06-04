package br.com.cadeiralivreempresaapi.modulos.agenda.dto;

import br.com.cadeiralivreempresaapi.modulos.agenda.dto.servico.ServicoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.cadeiralivreempresaapi.modulos.agenda.mocks.ServicoMocks.umServico;
import static org.assertj.core.api.Assertions.assertThat;

public class ServicoResponseTest {
    @Test
    @DisplayName("Deve converter para DTO de ServicoResponse quando informar Model de Servico")
    public void of_deveConverterParaDtoServicoResponse_quandoInformarModelDeServico() {
        var response = ServicoResponse.of(umServico());
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getPreco()).isEqualTo(25.00);
        assertThat(response.getEmpresa()).isEqualTo("Empresa 01");
        assertThat(response.getDescricao()).isEqualTo("Corte de cabelo");
        assertThat(response.getCpfCnpj()).isEqualTo("82.765.926/0001-32");
    }
}
