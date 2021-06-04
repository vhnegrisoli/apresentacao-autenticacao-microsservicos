package com.b2vnradarapi.b2vnradarapi.modules.radar.service;

import com.b2vnradarapi.b2vnradarapi.config.exception.ValidacaoException;
import com.b2vnradarapi.b2vnradarapi.modules.radar.dto.RadarLocalizacaoResponse;
import com.b2vnradarapi.b2vnradarapi.modules.radar.model.BaseRadares;
import com.b2vnradarapi.b2vnradarapi.modules.radar.repository.BaseRadaresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalizacaoService {

    @Autowired
    private BaseRadaresRepository baseRadaresRepository;

    public BaseRadares buscarPorLocalizacao(String latitudeLongitude) {
        return baseRadaresRepository.findByLatitudeLIgnoreCaseContaining(latitudeLongitude)
            .orElseThrow(() -> new ValidacaoException("Radar não encontrado para os pontos "
                + latitudeLongitude));
    }

    public List<RadarLocalizacaoResponse> buscarLocalizacoesMapa() {
        return baseRadaresRepository
            .findAll()
            .stream()
            .map(RadarLocalizacaoResponse::of)
            .collect(Collectors.toList());
    }

    public List<RadarLocalizacaoResponse> buscarLocalizacoesMapaComLote(Integer lote) {
        return baseRadaresRepository
            .findByLote(lote)
            .stream()
            .map(RadarLocalizacaoResponse::of)
            .collect(Collectors.toList());
    }

    public List<RadarLocalizacaoResponse> buscarPorLotes(List<Integer> lotes) {
        return baseRadaresRepository
            .findByLoteIn(lotes)
            .stream()
            .map(RadarLocalizacaoResponse::of)
            .collect(Collectors.toList());
    }

    public List<RadarLocalizacaoResponse> buscarLocalizacoesMapaComEnquadramentos(String enquadramento) {
        return baseRadaresRepository.findByEnquadrame(enquadramento)
            .stream()
            .map(RadarLocalizacaoResponse::of)
            .collect(Collectors.toList());
    }
}
