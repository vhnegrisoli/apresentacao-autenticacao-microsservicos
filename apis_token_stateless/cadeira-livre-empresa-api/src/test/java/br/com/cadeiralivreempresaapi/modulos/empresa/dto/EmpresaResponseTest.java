package br.com.cadeiralivreempresaapi.modulos.empresa.dto;

import br.com.cadeiralivreempresaapi.modulos.empresa.enums.ESituacaoEmpresa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static br.com.cadeiralivreempresaapi.modulos.empresa.mocks.EmpresaMocks.umaEmpresa;
import static org.assertj.core.api.Assertions.assertThat;

public class EmpresaResponseTest {

    @Test
    @DisplayName("Deve converter para DTO de EmpresaResponse quando informar Model de Empresa")
    public void of_deveConverterParaResponseEmpresa_quandoInformarModelEmpresa() {
        var response = EmpresaResponse.of(umaEmpresa(), Collections.emptyList());
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getNome()).isEqualTo("Empresa 01");
        assertThat(response.getRazaoSocial()).isEqualTo("Empresa 01");
        assertThat(response.getSituacao()).isEqualTo(ESituacaoEmpresa.ATIVA);
        assertThat(response.getCpfCnpj()).isEqualTo("82.765.926/0001-32");
    }
}
