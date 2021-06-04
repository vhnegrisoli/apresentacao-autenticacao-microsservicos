package com.b2vnradarapi.b2vnradarapi.modules.radar.repository;

import com.b2vnradarapi.b2vnradarapi.modules.radar.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface ContagensRepositoryCustom {

    RadarContagemResponse findFluxoVeiculosByCodigo(Integer codigo);

    RadarContagemResponse findFluxoVeiculosByCodigoAndDataHora(Integer codigo, LocalDateTime dataHoraInicial,
                                                               LocalDateTime dataHoraFinal);

    List<TiposPorRadarResponse> findTiposPorRadar(Integer localidade);

    List<TiposRadarTotais> findTipos();

    List<ContagensInfracoesResponse> findAutuacoesRadares();

    ContagensInfracoesResponse findAutuacoesPorRadar(Integer codigoRadar);

    ContagensAcuraciaResponse findAcuraciaPorRadar(Integer codigoRadar);

    List<ContagensAcuraciaResponse> findAcuraciaPorRadares();
}
