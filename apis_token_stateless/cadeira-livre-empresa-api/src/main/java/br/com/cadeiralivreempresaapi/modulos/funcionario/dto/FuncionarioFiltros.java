package br.com.cadeiralivreempresaapi.modulos.funcionario.dto;

import br.com.cadeiralivreempresaapi.modulos.funcionario.predicate.FuncionarioPredicate;
import br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESituacaoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioFiltros {

    private Integer usuarioId;
    private String nome;
    private String email;
    private String cpf;
    private ESituacaoUsuario situacao;
    private String empresa;
    private String razaoSocial;
    private String cpfCnpj;
    private Integer socioId;

    public FuncionarioPredicate toPredicate() {
        return new FuncionarioPredicate()
            .comUsuarioId(usuarioId)
            .comNome(nome)
            .comEmail(email)
            .comCpf(cpf)
            .comSituacao(situacao)
            .comEmpresa(empresa)
            .comRazaoSocial(razaoSocial)
            .comCnpj(cpfCnpj)
            .comSocioId(socioId);
    }
}
