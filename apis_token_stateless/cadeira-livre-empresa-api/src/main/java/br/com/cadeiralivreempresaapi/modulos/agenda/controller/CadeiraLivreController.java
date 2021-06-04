package br.com.cadeiralivreempresaapi.modulos.agenda.controller;

import br.com.cadeiralivreempresaapi.modulos.agenda.dto.cadeiralivre.CadeiraLivreRequest;
import br.com.cadeiralivreempresaapi.modulos.agenda.dto.cadeiralivre.CadeiraLivreResponse;
import br.com.cadeiralivreempresaapi.modulos.agenda.service.CadeiraLivreService;
import br.com.cadeiralivreempresaapi.modulos.comum.response.SuccessResponseDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cadeiras-livres")
public class CadeiraLivreController {

    @Autowired
    private CadeiraLivreService service;

    @GetMapping("{id}/empresa/{empresaId}")
    public CadeiraLivreResponse buscarCadeiraLivrePorIdEPorEmpresaId(@PathVariable Integer id,
                                                                     @PathVariable Integer empresaId) {
        return service.buscarCadeiraLivreResponsePorIdEPorEmpresaId(id, empresaId);
    }

    @GetMapping("empresa/{empresaId}")
    public List<CadeiraLivreResponse> buscarCadeirasLivresPorEmpresa(@PathVariable Integer empresaId) {
        return service.buscarCadeirasLivresPorEmpresa(empresaId);
    }

    @PostMapping("disponibilizar")
    public CadeiraLivreResponse disponibilizarCadeiraLivre(@RequestBody CadeiraLivreRequest request) {
        return service.disponibilizarCadeiraLivre(request);
    }

    @PutMapping("{id}/empresa/{empresaId}/indisponibilizar")
    public SuccessResponseDetails indisponibilizarCadeiraLivre(@PathVariable Integer id,
                                                               @PathVariable Integer empresaId) {
        return service.indisponibilizarCadeiraLivre(id, empresaId);
    }

    @PostMapping("tempo-expirado/indisponibilizar")
    public void indisponibilizarCadeirasLivresExpiradas() {
        service.indisponibilizarCadeirasLivresExpiradas(true);
    }

    @GetMapping("cliente-api")
    public List<CadeiraLivreResponse> buscarCadeirasLivresDoCliente(@RequestParam("token") String token) {
        return service.buscarCadeirasLivresDoCliente(token);
    }

    @GetMapping("disponiveis/cliente-api")
    public List<CadeiraLivreResponse> buscarCadeirasLivresDisponiveis(@RequestParam("token") String token,
                                                                      @RequestParam(value = "empresaId", required = false)
                                                                          Integer empresaId) {
        return service.buscarCadeirasLivresDisponiveis(token, empresaId);
    }

    @GetMapping("{id}/cliente-api")
    public CadeiraLivreResponse buscarCadeiraLivrePorId(@PathVariable Integer id,
                                                        @RequestParam("token") String token) {
        return service.buscarCadeiraLivrePorId(id, token);
    }

    @PostMapping("{id}/cliente-api/reservar")
    public CadeiraLivreResponse reservarCadeiraLivreParaCliente(@PathVariable Integer id,
                                                                @RequestParam("token") String token,
                                                                @RequestParam("cartaoId") String cartaoId) {
        return service.reservarCadeiraLivreParaCliente(id, token, cartaoId);
    }
}