package br.com.cadeiralivreempresaapi.modulos.empresa.dto;

import br.com.cadeiralivreempresaapi.modulos.empresa.enums.ESituacaoEmpresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.enums.ETipoEmpresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.predicate.EmpresaPredicate;
import lombok.Data;

@Data
public class EmpresaFiltros {

    private Integer id;
    private String cpfCnpj;
    private String nome;
    private ETipoEmpresa tipoEmpresa;
    private ESituacaoEmpresa situacao;
    private Integer socioId;

    public EmpresaPredicate toPredicate() {
        return new EmpresaPredicate()
            .comId(id)
            .comCnpj(cpfCnpj)
            .comNome(nome)
            .comTipoEmpresa(tipoEmpresa)
            .comSituacao(situacao)
            .comSocioId(socioId);
    }
}
