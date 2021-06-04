package br.com.cadeiralivreempresaapi.modulos.agenda.controller;

import br.com.cadeiralivreempresaapi.modulos.agenda.dto.servico.ServicoRequest;
import br.com.cadeiralivreempresaapi.modulos.agenda.dto.servico.ServicoResponse;
import br.com.cadeiralivreempresaapi.modulos.agenda.service.ServicoService;
import br.com.cadeiralivreempresaapi.modulos.comum.response.SuccessResponseDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @PostMapping
    public ServicoResponse salvarNovoServico(@RequestBody ServicoRequest request) {
        return servicoService.salvarNovoServico(request);
    }

    @PutMapping("{id}")
    public ServicoResponse atualizarServico(@RequestBody ServicoRequest request,
                                                   @PathVariable Integer id) {
        return servicoService.atualizarServico(request, id);
    }

    @GetMapping("empresa/{empresaId}")
    public List<ServicoResponse> buscarServicosPorEmpresa(@PathVariable Integer empresaId) {
        return servicoService.buscarServicosPorEmpresa(empresaId);
    }

    @GetMapping("{id}")
    public ServicoResponse buscarServicoPorId(@PathVariable Integer id) {
        return servicoService.buscarServicoPorId(id);
    }

    @DeleteMapping("{id}")
    public SuccessResponseDetails removerServicoPorId(@PathVariable Integer id) {
        return servicoService.removerServicoPorId(id);
    }
}
