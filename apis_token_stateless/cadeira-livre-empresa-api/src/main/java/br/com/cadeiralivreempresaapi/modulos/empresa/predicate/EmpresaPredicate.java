package br.com.cadeiralivreempresaapi.modulos.empresa.predicate;

import br.com.cadeiralivreempresaapi.config.PredicateBase;
import br.com.cadeiralivreempresaapi.modulos.empresa.enums.ESituacaoEmpresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.enums.ETipoEmpresa;

import static br.com.cadeiralivreempresaapi.modulos.empresa.model.QEmpresa.empresa;
import static org.springframework.util.ObjectUtils.isEmpty;

@SuppressWarnings("PMD.TooManyStaticImports")
public class EmpresaPredicate extends PredicateBase {

    public EmpresaPredicate comId(Integer id) {
        if (!isEmpty(id)) {
            builder.and(empresa.id.eq(id));
        }
        return this;
    }

    public EmpresaPredicate comCnpj(String cnpj) {
        if (!isEmpty(cnpj)) {
            builder.and(empresa.cpfCnpj.containsIgnoreCase(cnpj));
        }
        return this;
    }

    public EmpresaPredicate comNome(String nome) {
        if (!isEmpty(nome)) {
            builder.and(empresa.nome.containsIgnoreCase(nome));
        }
        return this;
    }

    public EmpresaPredicate comTipoEmpresa(ETipoEmpresa tipoEmpresa) {
        if (!isEmpty(tipoEmpresa)) {
            builder.and(empresa.tipoEmpresa.eq(tipoEmpresa));
        }
        return this;
    }

    public EmpresaPredicate comSituacao(ESituacaoEmpresa situacao) {
        if (!isEmpty(situacao)) {
            builder.and(empresa.situacao.eq(situacao));
        }
        return this;
    }

    public EmpresaPredicate comSocioId(Integer socioId) {
        if (!isEmpty(socioId)) {
            builder.and(empresa.socios.any().id.eq(socioId));
        }
        return this;
    }
}