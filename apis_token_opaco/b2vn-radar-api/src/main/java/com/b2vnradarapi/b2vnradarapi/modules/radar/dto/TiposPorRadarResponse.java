package com.b2vnradarapi.b2vnradarapi.modules.radar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TiposPorRadarResponse {

    String codigoRadar;
    String tipo;
    Long total;

    public TiposPorRadarResponse(Integer codigo, Integer tipo, Long total) {
        this.codigoRadar = codigo.toString();
        this.tipo = tipo == 0 ? "MOTO" : tipo == 1 ? "PASSEIO" : tipo == 2 ? "ONIBUS" : "CAMINHAO";
        this.total = total;
    }
}
