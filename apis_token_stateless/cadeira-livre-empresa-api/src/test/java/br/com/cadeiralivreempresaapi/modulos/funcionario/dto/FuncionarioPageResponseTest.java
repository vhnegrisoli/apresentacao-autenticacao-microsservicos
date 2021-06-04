package br.com.cadeiralivreempresaapi.modulos.funcionario.dto;

import br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESituacaoUsuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.cadeiralivreempresaapi.modulos.funcionario.mocks.FuncionarioMocks.umFuncionario;
import static org.assertj.core.api.Assertions.assertThat;

public class FuncionarioPageResponseTest {

    @Test
    @DisplayName("Deve converter para DTO FuncionarioPageResponse quando receber Model de Funcionário")
    public void of_deveConverterParaDtoFuncionarioPageResponse_quandoEnviarModelFuncionario() {
        var response = FuncionarioPageResponse.of(umFuncionario());
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getNome()).isEqualTo("Usuario");
        assertThat(response.getEmail()).isEqualTo("usuario@gmail.com");
        assertThat(response.getEmpresa()).isEqualTo("Empresa 01");
        assertThat(response.getCpfCnpj()).isEqualTo("82.765.926/0001-32");
        assertThat(response.getSituacao()).isEqualTo(ESituacaoUsuario.ATIVO);
    }
}
