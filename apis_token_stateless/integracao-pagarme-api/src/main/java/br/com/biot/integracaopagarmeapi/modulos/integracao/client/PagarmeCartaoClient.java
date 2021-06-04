package br.com.biot.integracaopagarmeapi.modulos.integracao.client;

import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.cartao.CartaoClientRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.cartao.CartaoClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(
    name = "pagarmeCartaoClient",
    contextId = "pagarmeCartaoClient",
    url = "${pagarme.cartoes.uri}")
public interface PagarmeCartaoClient {

    @PostMapping
    Optional<CartaoClientResponse> salvarCartao(@RequestBody CartaoClientRequest request);

    @GetMapping("{cardId}")
    Optional<CartaoClientResponse> buscarCartaoPorId(@PathVariable String cardId,
                                                     @RequestParam(name = "api_key") String apiKey);
}
