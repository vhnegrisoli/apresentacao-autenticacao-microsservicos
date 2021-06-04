package br.com.cadeiralivreempresaapi.modulos.funcionario.dto;

import br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESituacaoUsuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.cadeiralivreempresaapi.modulos.funcionario.mocks.FuncionarioMocks.umFuncionario;
import static org.assertj.core.api.Assertions.assertThat;

public class FuncionarioResponseTest {

    @Test
    @DisplayName("Deve converter para DTO FuncionarioResponse quando receber Model de Funcionário")
    public void of_deveConverterParaDtoFuncionarioResponse_quandoEnviarModelFuncionario() {
        var response = FuncionarioResponse.of(umFuncionario());
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getUsuarioId()).isEqualTo(1);
        assertThat(response.getNome()).isEqualTo("Usuario");
        assertThat(response.getEmail()).isEqualTo("usuario@gmail.com");
        assertThat(response.getCpf()).isEqualTo("332.368.250-57");
        assertThat(response.getEmpresa()).isEqualTo("Empresa 01");
        assertThat(response.getCpfCnpj()).isEqualTo("82.765.926/0001-32");
        assertThat(response.getSituacao()).isEqualTo(ESituacaoUsuario.ATIVO);
    }
}
