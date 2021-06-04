package com.b2vnradarapi.b2vnradarapi.modules.acidentes.enums;

import lombok.Getter;

public enum ETipoVeiculo {

    MOTO("Moto"),
    PASSEIO("Passeio"),
    ONIBUS("Ônibus"),
    CAMINHAO("Caminhão");

    @Getter
    private String tipo;

    ETipoVeiculo(String tipo) {
        this.tipo = tipo;
    }
}
