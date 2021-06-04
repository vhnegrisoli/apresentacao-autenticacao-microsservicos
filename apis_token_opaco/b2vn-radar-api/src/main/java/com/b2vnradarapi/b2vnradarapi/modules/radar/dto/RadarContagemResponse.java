package com.b2vnradarapi.b2vnradarapi.modules.radar.dto;

import com.b2vnradarapi.b2vnradarapi.modules.radar.model.BaseRadares;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RadarContagemResponse {

    private Integer fluxoVeiculos;
    private Long totalRegistros;
    private BaseRadares baseRadares;

    public RadarContagemResponse(Integer fluxoVeiculos, Long totalRegistros) {
        this.fluxoVeiculos = fluxoVeiculos;
        this.totalRegistros = totalRegistros;
    }
}
