package br.com.cadeiralivreempresaapi.modulos.transacao.client;

import br.com.cadeiralivreempresaapi.modulos.transacao.dto.TransacaoRequest;
import br.com.cadeiralivreempresaapi.modulos.transacao.dto.TransacaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(
    name = "integracaoPagarmeClient",
    contextId = "integracaoPagarmeClient",
    url = "${app-config.services.integracao-pagarme.transacao.url}"
)
public interface IntegracaoPagarmeClient {

    @PostMapping
    Optional<TransacaoResponse> realizarTransacao(@RequestBody TransacaoRequest transacaoRequest,
                                                  @RequestHeader String authorization);

    @GetMapping("{transacaoId}")
    Optional<TransacaoResponse> buscarTransacaoPorTransacaoId(@PathVariable Long transacaoId,
                                                              @RequestHeader String authorization);
}
