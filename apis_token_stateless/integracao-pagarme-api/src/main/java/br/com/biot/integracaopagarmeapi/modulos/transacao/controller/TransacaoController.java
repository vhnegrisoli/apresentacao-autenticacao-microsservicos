package br.com.biot.integracaopagarmeapi.modulos.transacao.controller;

import br.com.biot.integracaopagarmeapi.modulos.transacao.dto.TransacaoRequest;
import br.com.biot.integracaopagarmeapi.modulos.transacao.dto.TransacaoResponse;
import br.com.biot.integracaopagarmeapi.modulos.transacao.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transacao")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping
    public TransacaoResponse salvarTransacao(@RequestBody TransacaoRequest transacaoRequest) {
        return transacaoService.salvarTransacao(transacaoRequest);
    }

    @GetMapping("{transacaoId}")
    public TransacaoResponse buscarTransacaoPorTransacaoId(@PathVariable Long transacaoId) {
        return transacaoService.buscarTransacaoPorTransacaoId(transacaoId);
    }
}
