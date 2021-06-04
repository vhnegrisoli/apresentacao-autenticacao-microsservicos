package br.com.biot.integracaopagarmeapi.modulos.integracao.client;

import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao.TransacaoCapturaRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao.TransacaoClientRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao.TransacaoClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(
    name = "pagarmeTransacaoClient",
    contextId = "pagarmeTransacaoClient",
    url = "${pagarme.transacoes.uri}")
public interface PagarmeTransacaoClient {

    @PostMapping
    Optional<TransacaoClientResponse> salvarTransacao(@RequestBody TransacaoClientRequest request);

    @PostMapping("{transaction_id}/capture")
    Optional<TransacaoClientResponse> capturarTransacao(@PathVariable(name = "transaction_id") Long transactionId,
                                                        @RequestBody TransacaoCapturaRequest apiKeyRequest);

    @GetMapping("{transaction_id}")
    Optional<TransacaoClientResponse> buscarTransacaoPorId(@PathVariable(name = "transaction_id") Long transactionId,
                                                           @RequestParam(name = "api_key") String apiKey);
}
