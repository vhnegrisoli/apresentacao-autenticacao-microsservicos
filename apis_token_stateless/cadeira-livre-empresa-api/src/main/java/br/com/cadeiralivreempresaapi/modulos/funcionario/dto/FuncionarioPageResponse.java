package br.com.cadeiralivreempresaapi.modulos.funcionario.dto;

import br.com.cadeiralivreempresaapi.modulos.funcionario.model.Funcionario;
import br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESituacaoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioPageResponse {

    private Integer id;
    private String nome;
    private String email;
    private ESituacaoUsuario situacao;
    private String empresa;
    private String cpfCnpj;

    public static FuncionarioPageResponse of(Funcionario funcionario) {
        return FuncionarioPageResponse
            .builder()
            .id(funcionario.getId())
            .nome(funcionario.getUsuario().getNome())
            .email(funcionario.getUsuario().getEmail())
            .situacao(funcionario.getUsuario().getSituacao())
            .empresa(funcionario.getEmpresa().getNome())
            .cpfCnpj(funcionario.getEmpresa().getCpfCnpj())
            .build();
    }
}
