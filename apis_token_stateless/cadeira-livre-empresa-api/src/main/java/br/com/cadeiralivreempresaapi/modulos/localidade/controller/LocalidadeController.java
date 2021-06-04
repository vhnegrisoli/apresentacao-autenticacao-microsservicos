package br.com.cadeiralivreempresaapi.modulos.localidade.controller;

import br.com.cadeiralivreempresaapi.modulos.localidade.dto.CepResponse;
import br.com.cadeiralivreempresaapi.modulos.localidade.dto.CidadeResponse;
import br.com.cadeiralivreempresaapi.modulos.localidade.dto.EstadoResponse;
import br.com.cadeiralivreempresaapi.modulos.localidade.service.LocalidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/localidade")
public class LocalidadeController {

    @Autowired
    private LocalidadeService localidadeService;

    @GetMapping("cep/{cep}")
    public CepResponse consultarDadosPorCep(@PathVariable String cep) {
        return localidadeService.consultarDadosPorCep(cep);
    }

    @GetMapping("estados")
    public List<EstadoResponse> consultarEstados() {
        return localidadeService.consultarEstados();
    }

    @GetMapping("cidade/{estadoUf}")
    public List<CidadeResponse> consultarCidadesPorEstadoUf(@PathVariable String estadoUf) {
        return localidadeService.consultarCidadesPorEstadoUf(estadoUf);
    }
}