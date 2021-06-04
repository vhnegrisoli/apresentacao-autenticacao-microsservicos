package br.com.cadeiralivreempresaapi.modulos.empresa.dto;

import br.com.cadeiralivreempresaapi.modulos.empresa.enums.ESituacaoEmpresa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.cadeiralivreempresaapi.modulos.empresa.mocks.EmpresaMocks.umaEmpresa;
import static org.assertj.core.api.Assertions.assertThat;

public class EmpresaPageResponseTest {

    @Test
    @DisplayName("Deve converter para DTO de EmpresaPageResponse quando informar Model de Empresa")
    public void of_deveConverterParaResponsePaginadoEmpresa_quandoInformarModelEmpresa() {
        var response = EmpresaPageResponse.of(umaEmpresa());
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getNome()).isEqualTo("Empresa 01");
        assertThat(response.getSituacao()).isEqualTo(ESituacaoEmpresa.ATIVA);
        assertThat(response.getCpfCnpj()).isEqualTo("82.765.926/0001-32");
    }
}
