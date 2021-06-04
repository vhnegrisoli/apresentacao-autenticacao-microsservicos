package br.com.cadeiralivreempresaapi.modulos.transacao.service;

import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.agenda.model.Agenda;
import br.com.cadeiralivreempresaapi.modulos.empresa.model.Endereco;
import br.com.cadeiralivreempresaapi.modulos.empresa.service.EnderecoService;
import br.com.cadeiralivreempresaapi.modulos.jwt.dto.JwtUsuarioResponse;
import br.com.cadeiralivreempresaapi.modulos.transacao.client.IntegracaoPagarmeClient;
import br.com.cadeiralivreempresaapi.modulos.transacao.dto.TransacaoRequest;
import br.com.cadeiralivreempresaapi.modulos.transacao.dto.TransacaoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransacaoService {

    @Autowired
    private IntegracaoPagarmeClient integracaoPagarmeClient;
    @Autowired
    private EnderecoService enderecoService;

    public TransacaoResponse realizarTransacao(Agenda cadeiraLivre,
                                               String cartaoId,
                                               JwtUsuarioResponse jwtUsuarioResponse,
                                               String token) {
        var endereco = enderecoService
            .buscarEnderecosDaEmpresa(cadeiraLivre.getEmpresa().getId())
            .stream()
            .findFirst()
            .orElse(new Endereco());
        var transacaoRequest = TransacaoRequest.converterDe(cadeiraLivre, jwtUsuarioResponse, endereco, cartaoId);
        return realizarTransacao(transacaoRequest, token);
    }

    private TransacaoResponse realizarTransacao(TransacaoRequest request, String token) {
        try {
            log.info("Chamando API de Integração com a Pagar.me para realizar transação com dados: ".concat(request.toJson()));
            var respose = integracaoPagarmeClient
                .realizarTransacao(request, token)
                .orElseThrow(() -> new ValidacaoException("Não foi possível realizar o pagamento da Cadeira Livre."));
            log.info("Resposta para a chamada da API de Integração com a Pagar.me: ".concat(respose.toJson()));
            return respose;
        } catch (Exception ex) {
            log.error("Erro ao tentar realizar transação na Pagar.me: ", ex);
            throw new ValidacaoException("Erro interno ao tentar se comunicar com a Pagar.me API: ".concat(ex.getMessage()));
        }
    }

    public TransacaoResponse buscarTransacao(Long transacaoId, String token) {
        try {
            log.info("Chamando API de Integração com a Pagar.me para buscar transação por ID: "
                .concat(String.valueOf(transacaoId)));
            var respose = integracaoPagarmeClient
                .buscarTransacaoPorTransacaoId(transacaoId, token)
                .orElseThrow(() -> new ValidacaoException("Não foi possível buscar o pagamento da Cadeira Livre."));
            log.info("Resposta para a chamada da API de Integração com a Pagar.me: ".concat(respose.toJson()));
            return respose;
        } catch (Exception ex) {
            log.error("Erro ao tentar buscar transação na Pagar.me: ", ex);
            throw new ValidacaoException("Erro interno ao tentar se comunicar com a Pagar.me API: ".concat(ex.getMessage()));
        }
    }
}
