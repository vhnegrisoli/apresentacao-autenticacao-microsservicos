package com.b2vnradarapi.b2vnradarapi.modules.radar.controller;

import com.b2vnradarapi.b2vnradarapi.modules.radar.dto.ContagensAcuraciaResponse;
import com.b2vnradarapi.b2vnradarapi.modules.radar.dto.ContagensInfracoesResponse;
import com.b2vnradarapi.b2vnradarapi.modules.radar.dto.RadarContagemResponse;
import com.b2vnradarapi.b2vnradarapi.modules.radar.service.ContagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/radares/contagens")
public class ContagemController {

    @Autowired
    private ContagemService contagemService;

    @GetMapping("fluxo-veiculo/{codigoRadar}")
    public RadarContagemResponse buscarFluxoVeiculosPorCodigoRadar(@PathVariable Integer codigoRadar) {
        return contagemService.buscarFluxoVeiculos(codigoRadar);
    }

    @GetMapping("infracoes-totais")
    public Page<ContagensInfracoesResponse> buscarTodasAsInfracoesPorRadares(@RequestParam("page") Integer page,
                                                                             @RequestParam("size") Integer size) {
        return contagemService.buscarInfracoesPorRadares(page, size);
    }

    @GetMapping("infracoes/radar/{codigoRadar}")
    public ContagensInfracoesResponse buscarTodasAsInfracoesPorRadares(@PathVariable Integer codigoRadar) {
        return contagemService.buscarInfracoesPorRadar(codigoRadar);
    }

    @GetMapping("acuracia-totais")
    public Page<ContagensAcuraciaResponse> buscarTodasAsAcuraciasPorRadares(@RequestParam("page") Integer page,
                                                                            @RequestParam("size") Integer size) {
        return contagemService.buscarAcuraciaPorRadares(page, size);
    }

    @GetMapping("acuracia/radar/{codigoRadar}")
    public ContagensAcuraciaResponse buscarTodasAsAcuraciasPorRadares(@PathVariable Integer codigoRadar) {
        return contagemService.buscarAcuarciaPorRadar(codigoRadar);
    }
}
