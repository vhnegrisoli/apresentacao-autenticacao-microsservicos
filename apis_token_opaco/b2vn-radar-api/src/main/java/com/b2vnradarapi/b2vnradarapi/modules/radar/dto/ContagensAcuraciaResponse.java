package com.b2vnradarapi.b2vnradarapi.modules.radar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContagensAcuraciaResponse {

    private static DecimalFormat df = new DecimalFormat("0.00");
    private static final Integer CEM_POR_CENTO = 100;
    private static final Integer NUMERO_CASAS_DECIMAIS = 2;

    private String codigoRadar;
    private Integer totalPlacas;
    private Integer totalRegistrado;
    private BigDecimal acuracia;

    public ContagensAcuraciaResponse(Integer localidade, Integer totalPlacas, Integer totalRegistrado) {
        this.codigoRadar = localidade.toString();
        this.totalPlacas = totalPlacas;
        this.totalRegistrado = totalRegistrado;
        this.acuracia = new BigDecimal(totalRegistrado == 0
            ? 0 : ((double) totalPlacas / totalRegistrado) * CEM_POR_CENTO)
            .setScale(NUMERO_CASAS_DECIMAIS, RoundingMode.HALF_UP);
    }
}
