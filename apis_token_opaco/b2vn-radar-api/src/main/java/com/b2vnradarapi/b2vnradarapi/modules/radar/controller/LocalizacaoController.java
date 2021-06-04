package com.b2vnradarapi.b2vnradarapi.modules.radar.controller;

import com.b2vnradarapi.b2vnradarapi.modules.radar.dto.RadarLocalizacaoResponse;
import com.b2vnradarapi.b2vnradarapi.modules.radar.model.BaseRadares;
import com.b2vnradarapi.b2vnradarapi.modules.radar.service.LocalizacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/radares/localizacoes")
public class LocalizacaoController {

    @Autowired
    private LocalizacaoService localizacaoService;

    @GetMapping
    public BaseRadares buscarPorLocalizacao(@RequestParam("latitude_longitude") String latitudeLongitude) {
        return localizacaoService.buscarPorLocalizacao(latitudeLongitude);
    }

    @GetMapping("mapa")
    public List<RadarLocalizacaoResponse> buscarLocalizacoesMapa() {
        return localizacaoService.buscarLocalizacoesMapa();
    }

    @GetMapping("localizacoes/mapa/concessao/{lote}")
    public List<RadarLocalizacaoResponse> buscarLocalizacoesMapaComLote(@PathVariable Integer lote) {
        return localizacaoService.buscarLocalizacoesMapaComLote(lote);
    }

    @GetMapping("mapa/concessoes")
    public List<RadarLocalizacaoResponse> buscarPorLotes(@RequestParam("lotes") List<Integer> lotes) {
        return localizacaoService.buscarPorLotes(lotes);
    }

    @GetMapping("mapa/enquadramento/{enquadramento}")
    public List<RadarLocalizacaoResponse> buscarLocalizacoesMapaComEnquadramentos(@PathVariable String enquadramento) {
        return localizacaoService.buscarLocalizacoesMapaComEnquadramentos(enquadramento);
    }
}
