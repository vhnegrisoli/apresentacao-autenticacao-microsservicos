package br.com.biot.integracaopagarmeapi.modulos.integracao.service;

import br.com.biot.integracaopagarmeapi.config.exception.ValidacaoException;
import br.com.biot.integracaopagarmeapi.modulos.integracao.client.PagarmeCartaoClient;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.cartao.CartaoClientRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.cartao.CartaoClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IntegracaoCartaoService {

    @Autowired
    private PagarmeCartaoClient cartaoClient;

    @Value("${pagarme.api_keys.teste}")
    private String apiKey;

    public CartaoClientResponse salvarCartao(CartaoClientRequest request) {
        try {
            request.setApiKey(apiKey);
            log.info("Realizando chamada à API do Pagar.me para salvar o cartão com dados: ".concat(request.toJson()));
            var response = cartaoClient
                .salvarCartao(request)
                .orElseThrow(() -> new ValidacaoException("Erro ao tentar salvar cartão na Pagar.me."));
            log.info("Obtendo resposta da API do Pagar.me do cartão salvo: ".concat(response.toJson()));
            return response;
        } catch (Exception ex) {
            log.error("Erro ao tentar salvar cartão na API da Pagar.me: ", ex);
            throw new ValidacaoException("Erro interno ao tentar salvar cartão na Pagar.me.");
        }
    }

    public CartaoClientResponse buscarCartaoPorId(String cartaoId) {
        try {
            log.info("Realizando chamada à API do Pagar.me para buscar um cartão pelo ID: ".concat(cartaoId));
            var response = cartaoClient
                .buscarCartaoPorId(cartaoId, apiKey)
                .orElseThrow(() -> new ValidacaoException("Erro ao tentar buscar cartão por ID na Pagar.me."));
            log.info("Obtendo resposta da API do Pagar.me de cartão por ID: ".concat(response.toJson()));
            return response;
        } catch (Exception ex) {
            log.error("Erro ao tentar buscar cartão por ID na API da Pagar.me: ", ex);
            throw new ValidacaoException("Erro interno ao tentar buscar cartão por ID na Pagar.me.");
        }
    }
}
