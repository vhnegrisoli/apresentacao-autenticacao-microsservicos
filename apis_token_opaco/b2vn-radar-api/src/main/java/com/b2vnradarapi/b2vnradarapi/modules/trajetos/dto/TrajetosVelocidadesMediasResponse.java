package com.b2vnradarapi.b2vnradarapi.modules.trajetos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.springframework.util.ObjectUtils.isEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("PMD.AvoidDecimalLiteralsInBigDecimalConstructor")
public class TrajetosVelocidadesMediasResponse {

    private static final Integer MEDIA = 2;
    private static final Integer NUMERO_CASAS_DECIMAIS = 2;

    private String radarOrigem;
    private String enderecoOrigem;
    private Integer velocidadeOrigem;
    private String radarDestino;
    private Integer velocidadeDestino;
    private String enderecoDestino;
    private BigDecimal velocidadeMedia;

    public TrajetosVelocidadesMediasResponse(TrajetosResponse response) {
        this.radarOrigem = response.getTrajetoCompleto().getOrigem().toString();
        this.radarDestino = response.getTrajetoCompleto().getDestino().toString();
        this.enderecoOrigem = isEmpty(response.getRadarOrigem()) ? null : response.getRadarOrigem().getEndereco();
        this.enderecoDestino = isEmpty(response.getRadarDestino()) ? null : response.getRadarDestino().getEndereco();
        this.velocidadeOrigem = response.getTrajetoCompleto().getV0();
        this.velocidadeDestino = response.getTrajetoCompleto().getV1();
        this.velocidadeMedia = this.velocidadeOrigem == 0 && this.velocidadeDestino == 0 ? new BigDecimal(0.0) :
            new BigDecimal((double) (this.velocidadeOrigem + this.velocidadeDestino) / MEDIA)
                .setScale(NUMERO_CASAS_DECIMAIS, RoundingMode.HALF_UP);
    }
}
