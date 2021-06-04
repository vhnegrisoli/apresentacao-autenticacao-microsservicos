package br.com.cadeiralivreempresaapi.modulos.funcionario.mocks;

import br.com.cadeiralivreempresaapi.modulos.funcionario.dto.FuncionarioPageResponse;
import br.com.cadeiralivreempresaapi.modulos.funcionario.dto.FuncionarioResponse;
import br.com.cadeiralivreempresaapi.modulos.funcionario.model.Funcionario;

import java.time.LocalDateTime;

import static br.com.cadeiralivreempresaapi.modulos.empresa.mocks.EmpresaMocks.umaEmpresa;
import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.umUsuario;

public class FuncionarioMocks {

    public static Funcionario umFuncionario() {
        return Funcionario
            .builder()
            .id(1)
            .empresa(umaEmpresa())
            .usuario(umUsuario())
            .dataCadastro(LocalDateTime.now())
            .build();
    }

    public static FuncionarioResponse umFuncionarioResponse() {
        return FuncionarioResponse.of(umFuncionario());
    }

    public static FuncionarioPageResponse umFuncionarioPageResponse() {
        return FuncionarioPageResponse.of(umFuncionario());
    }
}
