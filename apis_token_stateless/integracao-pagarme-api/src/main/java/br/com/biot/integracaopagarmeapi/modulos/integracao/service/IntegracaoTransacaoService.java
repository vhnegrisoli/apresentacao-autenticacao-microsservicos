package br.com.biot.integracaopagarmeapi.modulos.integracao.service;

import br.com.biot.integracaopagarmeapi.config.exception.ValidacaoException;
import br.com.biot.integracaopagarmeapi.modulos.integracao.client.PagarmeTransacaoClient;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao.TransacaoCapturaRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao.TransacaoClientRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao.TransacaoClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class IntegracaoTransacaoService {

    @Autowired
    private PagarmeTransacaoClient transacaoClient;

    @Value("${pagarme.api_keys.teste}")
    private String apiKey;

    public TransacaoClientResponse salvarTransacao(TransacaoClientRequest request) {
        try {
            request.setApiKey(apiKey);
            log.info("Realizando chamada à API do Pagar.me para realizar a transação com dados: ".concat(request.toJson()));
            var response = transacaoClient
                .salvarTransacao(request)
                .orElseThrow(() -> new ValidacaoException("Erro ao tentar realizar transação na Pagar.me."));
            log.info("Obtendo resposta da API do Pagar.me da transação realizada: ".concat(response.toJson()));
            return response;
        } catch (Exception ex) {
            log.error("Erro ao tentar realizar transação na API da Pagar.me: ", ex);
            throw new ValidacaoException("Erro interno ao tentar realizar transação na Pagar.me.");
        }
    }

    public TransacaoClientResponse capturarTransacao(Long transacaoId, BigDecimal totalPagamento) {
        try {
            log.info("Realizando chamada à API do Pagar.me para capturar a transação: ".concat(String.valueOf(transacaoId)));
            var response = transacaoClient
                .capturarTransacao(transacaoId, TransacaoCapturaRequest.criar(apiKey, totalPagamento))
                .orElseThrow(() -> new ValidacaoException("Erro ao tentar capturar transação na Pagar.me."));
            log.info("Obtendo resposta da API do Pagar.me da transação capturada: ".concat(response.toJson()));
            return response;
        } catch (Exception ex) {
            log.error("Erro ao tentar capturar transação na API da Pagar.me: ", ex);
            throw new ValidacaoException("Erro interno ao tentar capturar transação na Pagar.me.");
        }
    }

    public TransacaoClientResponse buscarTransacaoPorId(Long transacaoId) {
        try {
            log.info("Realizando chamada à API do Pagar.me para buscar uma transação pelo ID: ".concat(String.valueOf(transacaoId)));
            var response = transacaoClient
                .buscarTransacaoPorId(transacaoId, apiKey)
                .orElseThrow(() -> new ValidacaoException("Erro ao tentar buscar transação por ID na Pagar.me."));
            log.info("Obtendo resposta da API do Pagar.me de transação por ID: ".concat(response.toJson()));
            return response;
        } catch (Exception ex) {
            log.error("Erro ao tentar buscar transação por ID na API da Pagar.me: ", ex);
            throw new ValidacaoException("Erro interno ao tentar buscar transação por ID na Pagar.me.");
        }
    }
}
