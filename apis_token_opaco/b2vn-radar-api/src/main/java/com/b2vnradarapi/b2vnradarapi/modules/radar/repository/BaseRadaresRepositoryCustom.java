package com.b2vnradarapi.b2vnradarapi.modules.radar.repository;

import com.b2vnradarapi.b2vnradarapi.modules.radar.dto.RadaresVelocidadeResponse;
import com.b2vnradarapi.b2vnradarapi.modules.radar.model.BaseRadares;

import java.util.List;

public interface BaseRadaresRepositoryCustom {

    List<Integer> findLoteDistict();

    List<String> findEnquadramentoDistict();

    List<String> findVelocidadeDistinct();

    List<BaseRadares> findRadaresByVelocidade(Integer velocidade);

    List<RadaresVelocidadeResponse> findTotalRadaresByVelocidade();
}
