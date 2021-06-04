package com.b2vnradarapi.b2vnradarapi.modules.radar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TiposRadarTotais {

    private String tipo;
    private Long total;

    public TiposRadarTotais(Integer tipo, Long total) {
        this.tipo = tipo == 0 ? "MOTO" : tipo == 1 ? "PASSEIO" : tipo == 2 ? "ONIBUS" : "CAMINHAO";
        this.total = total;
    }
}
