package br.com.cadeiralivreempresaapi.modulos.funcionario.predicate;

import br.com.cadeiralivreempresaapi.config.PredicateBase;
import br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESituacaoUsuario;

import static br.com.cadeiralivreempresaapi.modulos.funcionario.model.QFuncionario.funcionario;
import static org.springframework.util.ObjectUtils.isEmpty;

public class FuncionarioPredicate extends PredicateBase {

    public FuncionarioPredicate comUsuarioId(Integer id) {
        if (!isEmpty(id)) {
            builder.and(funcionario.usuario.id.eq(id));
        }
        return this;
    }

    public FuncionarioPredicate comNome(String nome) {
        if (!isEmpty(nome)) {
            builder.and(funcionario.usuario.nome.containsIgnoreCase(nome));
        }
        return this;
    }

    public FuncionarioPredicate comEmail(String email) {
        if (!isEmpty(email)) {
            builder.and(funcionario.usuario.email.containsIgnoreCase(email));
        }
        return this;
    }

    public FuncionarioPredicate comCpf(String cpf) {
        if (!isEmpty(cpf)) {
            builder.and(funcionario.usuario.cpf.containsIgnoreCase(cpf));
        }
        return this;
    }

    public FuncionarioPredicate comEmpresa(String empresa) {
        if (!isEmpty(empresa)) {
            builder.and(funcionario.empresa.nome.containsIgnoreCase(empresa));
        }
        return this;
    }

    public FuncionarioPredicate comCnpj(String cnpj) {
        if (!isEmpty(cnpj)) {
            builder.and(funcionario.empresa.cpfCnpj.containsIgnoreCase(cnpj));
        }
        return this;
    }

    public FuncionarioPredicate comSituacao(ESituacaoUsuario situacao) {
        if (!isEmpty(situacao)) {
            builder.and(funcionario.usuario.situacao.eq(situacao));
        }
        return this;
    }

    public FuncionarioPredicate comRazaoSocial(String razaoSocial) {
        if (!isEmpty(razaoSocial)) {
            builder.and(funcionario.empresa.razaoSocial.containsIgnoreCase(razaoSocial));
        }
        return this;
    }

    public FuncionarioPredicate comSocioId(Integer socioId) {
        if (!isEmpty(socioId)) {
            builder.and(funcionario.empresa.socios.any().id.eq(socioId));
        }
        return this;
    }
}
