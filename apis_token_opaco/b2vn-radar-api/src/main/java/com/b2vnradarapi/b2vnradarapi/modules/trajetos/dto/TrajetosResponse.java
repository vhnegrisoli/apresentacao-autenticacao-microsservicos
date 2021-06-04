package com.b2vnradarapi.b2vnradarapi.modules.trajetos.dto;

import com.b2vnradarapi.b2vnradarapi.modules.radar.model.BaseRadares;
import com.b2vnradarapi.b2vnradarapi.modules.trajetos.model.Trajetos;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Optional;

@Data
@NoArgsConstructor
public class TrajetosResponse {

    private Trajetos trajetoCompleto;
    private BaseRadares radarOrigem;
    private BaseRadares radarDestino;

    public TrajetosResponse(Trajetos trajetoCompleto, Optional<BaseRadares> radarOrigem,
                            Optional<BaseRadares> radarDestino) {
        this.trajetoCompleto = trajetoCompleto;
        this.radarOrigem = !radarOrigem.isEmpty() ? radarOrigem.get() : null;
        this.radarDestino = !radarDestino.isEmpty() ? radarDestino.get() : null;
    }
}
