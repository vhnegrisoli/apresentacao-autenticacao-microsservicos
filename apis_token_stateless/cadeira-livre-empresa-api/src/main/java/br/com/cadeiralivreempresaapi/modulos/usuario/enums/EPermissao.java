package br.com.cadeiralivreempresaapi.modulos.usuario.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EPermissao {

    ADMIN(1, "Administrador"),
    PROPRIETARIO(2, "Proprietário"),
    SOCIO(3, "Sócio"),
    FUNCIONARIO(4, "Funcionário"),
    PERMISSAO_NAO_MAPEADA(1000, "Não mapeada");

    @Getter
    private Integer id;
    @Getter
    private String descricao;
}
