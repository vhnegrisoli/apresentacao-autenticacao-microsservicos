package br.com.cadeiralivreempresaapi.modulos.funcionario.model;

import br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESituacaoUsuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.cadeiralivreempresaapi.modulos.empresa.mocks.EmpresaMocks.umaEmpresa;
import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.umUsuario;
import static org.assertj.core.api.Assertions.assertThat;

public class FuncionarioTest {

    @Test
    @DisplayName("Deve converter para Model de Funcionário quando receber Model de Usuario e Empresa")
    public void of_deveConverterParaModelFuncionario_quandoEnviarModelFuncionario() {
        var funcionario = Funcionario.of(umUsuario(), umaEmpresa());
        assertThat(funcionario).isNotNull();
        assertThat(funcionario.getUsuario().getId()).isEqualTo(1);
        assertThat(funcionario.getUsuario().getNome()).isEqualTo("Usuario");
        assertThat(funcionario.getUsuario().getEmail()).isEqualTo("usuario@gmail.com");
        assertThat(funcionario.getUsuario().getCpf()).isEqualTo("332.368.250-57");
        assertThat(funcionario.getUsuario().getSituacao()).isEqualTo(ESituacaoUsuario.ATIVO);
        assertThat(funcionario.getEmpresa().getNome()).isEqualTo("Empresa 01");
        assertThat(funcionario.getEmpresa().getCpfCnpj()).isEqualTo("82.765.926/0001-32");
    }
}
