package br.com.cadeiralivreempresaapi.modulos.usuario.predicate;

import br.com.cadeiralivreempresaapi.config.PredicateBase;
import br.com.cadeiralivreempresaapi.modulos.usuario.enums.EPermissao;
import com.querydsl.jpa.JPAExpressions;

import static br.com.cadeiralivreempresaapi.modulos.usuario.model.QPermissao.permissao1;
import static br.com.cadeiralivreempresaapi.modulos.usuario.model.QUsuario.usuario;
import static org.springframework.util.ObjectUtils.isEmpty;

public class UsuarioPredicate extends PredicateBase {

    public UsuarioPredicate comNome(String nome) {
        if (!isEmpty(nome)) {
            builder.and(usuario.nome.containsIgnoreCase(nome));
        }
        return this;
    }

    public UsuarioPredicate comEmail(String email) {
        if (!isEmpty(email)) {
            builder.and(usuario.email.containsIgnoreCase(email));
        }
        return this;
    }

    public UsuarioPredicate comCpf(String cpf) {
        if (!isEmpty(cpf)) {
            builder.and(usuario.cpf.containsIgnoreCase(cpf));
        }
        return this;
    }

    public UsuarioPredicate comPermissao(EPermissao permissao) {
        if (!isEmpty(permissao)) {
            builder.and(usuario.permissoes.contains(
                JPAExpressions
                    .selectFrom(permissao1)
                    .where(permissao1.permissao.eq(permissao))
                )
            );
        }
        return this;
    }
}