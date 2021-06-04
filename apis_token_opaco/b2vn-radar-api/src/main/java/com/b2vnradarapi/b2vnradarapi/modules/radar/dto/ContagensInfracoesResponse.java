package com.b2vnradarapi.b2vnradarapi.modules.radar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContagensInfracoesResponse {

    private String codigoRadar;
    private Integer totalInfracoes;

    public ContagensInfracoesResponse(Integer localidade, Integer totalInfracoes) {
        this.codigoRadar = localidade.toString();
        this.totalInfracoes = totalInfracoes;
    }
}
