package br.com.cadeiralivreempresaapi.modulos.usuario.dto;

import br.com.cadeiralivreempresaapi.modulos.usuario.enums.EPermissao;
import br.com.cadeiralivreempresaapi.modulos.usuario.predicate.UsuarioPredicate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioFiltros {

    private String nome;
    private String email;
    private String cpf;
    private EPermissao permissao;

    public UsuarioPredicate toPredicate() {
        return new UsuarioPredicate()
            .comNome(nome)
            .comEmail(email)
            .comCpf(cpf)
            .comPermissao(permissao);
    }
}
