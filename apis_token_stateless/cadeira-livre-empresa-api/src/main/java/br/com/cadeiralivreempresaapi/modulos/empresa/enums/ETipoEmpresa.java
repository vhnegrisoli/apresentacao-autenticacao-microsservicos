package br.com.cadeiralivreempresaapi.modulos.empresa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ETipoEmpresa {

    SALAO("Salão de Beleza"),
    CABELO("Cabeleireiro/a"),
    BARBEARIA("Barbearia");

    @Getter
    private String tipoEmpresa;
}
