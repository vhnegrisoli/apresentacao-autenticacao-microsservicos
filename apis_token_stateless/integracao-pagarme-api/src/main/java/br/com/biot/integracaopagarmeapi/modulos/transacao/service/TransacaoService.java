package br.com.biot.integracaopagarmeapi.modulos.transacao.service;

import br.com.biot.integracaopagarmeapi.config.exception.OperacaoProibidaException;
import br.com.biot.integracaopagarmeapi.config.exception.ValidacaoException;
import br.com.biot.integracaopagarmeapi.modulos.cartao.model.Cartao;
import br.com.biot.integracaopagarmeapi.modulos.cartao.service.CartaoService;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao.TransacaoClientRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao.TransacaoClientResponse;
import br.com.biot.integracaopagarmeapi.modulos.integracao.service.IntegracaoTransacaoService;
import br.com.biot.integracaopagarmeapi.modulos.jwt.dto.JwtUsuarioResponse;
import br.com.biot.integracaopagarmeapi.modulos.jwt.service.JwtService;
import br.com.biot.integracaopagarmeapi.modulos.transacao.dto.*;
import br.com.biot.integracaopagarmeapi.modulos.transacao.enums.TransacaoStatus;
import br.com.biot.integracaopagarmeapi.modulos.transacao.model.Transacao;
import br.com.biot.integracaopagarmeapi.modulos.transacao.repository.TransacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.biot.integracaopagarmeapi.modulos.transacao.enums.TransacaoStatus.possuiStatusValidos;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;
    @Autowired
    private IntegracaoTransacaoService integracaoTransacaoService;
    @Autowired
    private CartaoService cartaoService;
    @Autowired
    private JwtService jwtService;

    public TransacaoResponse salvarTransacao(TransacaoRequest transacaoRequest) {
        try {
            log.info("Realizando chamada ao endpoint de salvar e capturar transações com dados: ".concat(transacaoRequest.toJson()));
            validarDadosTransacao(transacaoRequest);
            var usuario = jwtService.recuperarUsuarioAutenticado();
            var cartao = cartaoService.buscarCartaoPorCartaoIdEUsuarioId(transacaoRequest.getCartaoId(), usuario.getId());
            var transacaoClientRequest =  TransacaoClientRequest.converterDe(usuario, transacaoRequest);
            var transacaoRealizada = realizarTransacaoPagarme(transacaoClientRequest);
            validarTransacaoAprovada(transacaoRealizada);
            var transacao = persistirTransacao(transacaoRequest, transacaoRealizada, usuario, cartao);
            capturarTransacaoPagarme(transacao);
            log.info("Resposta da chamada de realização de transações: ".concat(transacaoRealizada.toJson()));
            return TransacaoResponse.converterDe(transacao);
        } catch (Exception ex) {
            log.error("Erro ao salvar transação: ", ex);
            throw new ValidacaoException("Erro ao salvar transação: " + ex.getMessage());
        }
    }

    private TransacaoClientResponse realizarTransacaoPagarme(TransacaoClientRequest transacaoClientRequest) {
        return integracaoTransacaoService
            .salvarTransacao(transacaoClientRequest);
    }

    private void validarTransacaoAprovada(TransacaoClientResponse transacaoResponse) {
        var status = transacaoResponse.getStatus();
        if (isEmpty(status)) {
            throw new ValidacaoException("A transação ".concat(transacaoResponse.getIdStr()).concat(" não retornou status."));
        }
        log.info("Status da transação ".concat(transacaoResponse.getIdStr()).concat(": ").concat(status));
        if (!possuiStatusValidos(status)) {
            throw new ValidacaoException("A transação foi recusada na Pagar.me com status: ".concat(status));
        }
    }

    private Transacao capturarTransacaoPagarme(Transacao transacao) {
        if (transacao.isAutorizada()) {
            log.info(String.format("A transação %s está AUTORIZADA. Poderá ser feita a captura.", transacao.getTransacaoId()));
            var transacaoCapturada = integracaoTransacaoService
                .capturarTransacao(transacao.getTransacaoId(), transacao.getValorPagamento());
            atualizarStatusTransacao(transacaoCapturada);
            return transacao;
        }
        logarResultadoCaptura(transacao);
        return transacao;
    }

    private void logarResultadoCaptura(Transacao transacao) {

        if (transacao.isAnalise()) {
            log.info("A transação "
                .concat(String.valueOf(transacao.getTransacaoId()))
                .concat(" não pode ser capturada pois está em análise."));
        }
        if (transacao.isPaga()) {
            log.info("A transação "
                .concat(String.valueOf(transacao.getTransacaoId()))
                .concat(" já está paga e capturada."));
        }
    }

    @Transactional
    private void atualizarStatusTransacao(TransacaoClientResponse transacaoPagarme) {
        log.info("Atualizando o status da transação "
            .concat(transacaoPagarme.getIdStr())
            .concat(" para situação: ")
            .concat(transacaoPagarme.getStatus()));
        var transacaoExistente = buscarPorTransacaoId(transacaoPagarme.getId());
        transacaoExistente.setSituacaoTransacao(transacaoPagarme.getStatus());
        transacaoExistente.setTransacaoStatus(Transacao.definirTransacaoStatus(transacaoPagarme.getStatus()));
        transacaoRepository.save(transacaoExistente);
        log.info("Status da transação ".concat(transacaoPagarme.getIdStr()).concat(" atualizada com sucesso."));
    }

    @Transactional
    private Transacao persistirTransacao(TransacaoRequest transacaoRequest,
                                         TransacaoClientResponse transacaoResponse,
                                         JwtUsuarioResponse usuario,
                                         Cartao cartao) {
        log.info("Salvando a transação: ".concat(transacaoResponse.getIdStr()));
        transacaoRepository.save(Transacao.converterDe(usuario, transacaoRequest, transacaoResponse, cartao));
        log.info("Transação: ".concat(transacaoResponse.getIdStr()).concat(" salva com sucesso."));
        return buscarPorTransacaoId(transacaoResponse.getId());
    }

    private void validarDadosTransacao(TransacaoRequest request) {
        validarDadosRequest(request);
        validarDadosCobranca(request.getCobranca());
        validarDadosEndereco(request.getCobranca().getEndereco());
        request.getItens().forEach(this::validarDadosItem);
    }

    private void validarDadosRequest(TransacaoRequest request) {
        if (isEmpty(request)
            || isEmpty(request.getCartaoId())
            || isEmpty(request.getNumerosTelefone())
            || isEmpty(request.getCobranca())
            || isEmpty(request.getTotal())
            || isEmpty(request.getItens())) {
            throw new ValidacaoException("É necessário informar todos os seguintes campos: "
                .concat("cartaoId, números de telefone, cobranca, itens e total."));
        }
    }

    private void validarDadosCobranca(CobrancaRequest cobrancaRequest) {
        if (isEmpty(cobrancaRequest.getNome())
            || isEmpty(cobrancaRequest.getEndereco())) {
            throw new ValidacaoException("É necessário informar os campos de nome e endereço da entidade de cobrança.");
        }
    }

    private void validarDadosEndereco(EnderecoCobrancaRequest enderecoCobrancaRequest) {
        if (isEmpty(enderecoCobrancaRequest.getEstado())
            || isEmpty(enderecoCobrancaRequest.getCidade())
            || isEmpty(enderecoCobrancaRequest.getNumeroRua())
            || isEmpty(enderecoCobrancaRequest.getRua())
            || isEmpty(enderecoCobrancaRequest.getCep())) {
            throw new ValidacaoException("É necessário informar estado, cidade, rua,"
                + " número da rua e o cep do endereço de cobrança.");
        }
    }

    private void validarDadosItem(ItemTransacaoRequest itemTransacaoRequest) {
        if (isEmpty(itemTransacaoRequest.getId())
            || isEmpty(itemTransacaoRequest.getPrecoUnitario())
            || isEmpty(itemTransacaoRequest.getTitulo())
            || isEmpty(itemTransacaoRequest.getQuantidade())) {
         throw new ValidacaoException("É necessário informar os campos dos itens:"
             + " id, preço unitário, título e quantidade.");
        }
    }

    public Transacao buscarPorTransacaoId(Long transacaoId) {
        if (isEmpty(transacaoId)) {
            throw new ValidacaoException("É necessário informar o ID da transação.");
        }
        return transacaoRepository
            .findByTransacaoId(transacaoId)
            .orElseThrow(() -> new ValidacaoException("Não foi encontrada uma transação para o ID "
                .concat(String.valueOf(transacaoId))));
    }

    public TransacaoResponse buscarTransacaoPorTransacaoId(Long transacaoId) {
        var transacao = buscarPorTransacaoId(transacaoId);
        if (!jwtService.recuperarUsuarioAutenticado().getId().equals(transacao.getUsuarioId())) {
            throw new OperacaoProibidaException("Você não tem permissão para visualizar os dados dessa transação.");
        }
        return TransacaoResponse.converterDe(transacao);
    }

    public void capturarTransacoesAutorizadas() {
        var transacoesCaptura = transacaoRepository
            .findByTransacaoStatusInOrSituacaoTransacaoIn(
                TransacaoStatus.informarStatusValidosCaptura(),
                TransacaoStatus.informarSituacoesValidosCaptura()
            );
        if (isEmpty(transacoesCaptura)) {
            log.info("Não foram encontradas transações para análise de atualização de status ou captura.");
        } else {
            log.info(String.format("Foram encontradas %s transações para análise.", transacoesCaptura.size()));
            transacoesCaptura.forEach(this::capturarOuAtualizarStatusTransacao);
        }
    }

    private void capturarOuAtualizarStatusTransacao(Transacao transacao) {
        try {
            log.info(String.format("Analisando transação %s.", transacao.getTransacaoId()));
            validarCapturaTransacao(transacao);
            validarAtualizacaoSituacao(transacao);
        } catch (Exception ex) {
            log.error(String.format(
                "Erro ao tentar atualizar ou capturar o status da transação %s. Erro: ", transacao.getTransacaoId()),
                ex);
        }
    }

    private void validarCapturaTransacao(Transacao transacao) {
        if (transacao.isAutorizada()) {
            capturarTransacaoPagarme(transacao);
        }
    }

    private void validarAtualizacaoSituacao(Transacao transacao) {
        if (!transacao.isPaga()) {
            log.info(String.format(
                "A transação %s não está paga e nem autorizada. Buscando dados na Pagar.me para atualizar situação.",
                transacao.getTransacaoId()));
            var transacaoPagarme = integracaoTransacaoService.buscarTransacaoPorId(transacao.getTransacaoId());
            atualizarStatusTransacao(transacaoPagarme);
        }
    }
}
