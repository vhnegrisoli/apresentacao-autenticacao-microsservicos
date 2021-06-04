package br.com.cadeiralivreempresaapi.modulos.localidade.service;

import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.localidade.client.IbgeLocalidadeClient;
import br.com.cadeiralivreempresaapi.modulos.localidade.client.ViaCepClient;
import br.com.cadeiralivreempresaapi.modulos.localidade.dto.CepResponse;
import br.com.cadeiralivreempresaapi.modulos.localidade.dto.CidadeResponse;
import br.com.cadeiralivreempresaapi.modulos.localidade.dto.EstadoResponse;
import br.com.cadeiralivreempresaapi.modulos.localidade.dto.ViaCepResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.cadeiralivreempresaapi.modulos.comum.util.Constantes.PONTO;
import static br.com.cadeiralivreempresaapi.modulos.comum.util.Constantes.TRACO;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class LocalidadeService {



    @Autowired
    private ViaCepClient viaCepClient;

    @Autowired
    private IbgeLocalidadeClient ibgeLocalidadeClient;

    public CepResponse consultarDadosPorCep(String cep) {
        try {
            cep = tratarCep(cep);
            log.info(String.format("Consultando a API do ViaCep para dados do CEP %s.", cep));
            var cepNaoEncontrado = String.format("O cep %s não foi encontrado.", cep);
            var response =  viaCepClient
                .consultarDadosPorCep(cep)
                .orElseThrow(() -> new ValidacaoException(cepNaoEncontrado));
            validarCepComErro(response, cepNaoEncontrado);
            return CepResponse.converterDe(response);
        } catch (Exception ex) {
            var message = String.format("Erro ao tentar consultar o cep %s na API do ViaCep: %s", cep, ex.getMessage());
            log.error(message, ex);
            throw new ValidacaoException(message);
        }
    }

    private String tratarCep(String cep) {
        if (isEmpty(cep) || isEmpty(cep.trim())) {
            throw new ValidacaoException("O valor do CEP não foi informado.");
        }
        if (cep.contains(PONTO)) {
            cep = cep.replace(PONTO, Strings.EMPTY);
        }
        if (cep.contains(TRACO)) {
            cep = cep.replace(TRACO, Strings.EMPTY);
        }
        return cep;
    }

    private void validarCepComErro(ViaCepResponse response, String cepNaoEncontrado) {
        if (response.isErro()) {
            throw new ValidacaoException(cepNaoEncontrado);
        }
    }

    public List<EstadoResponse> consultarEstados() {
        try {
            log.info("Consultando a API de estados do IBGE.");
            return ibgeLocalidadeClient.consultarEstados();
        } catch (Exception ex) {
            var message = "Erro ao tentar consultar a API de estados do IBGE.";
            log.error(message, ex);
            throw new ValidacaoException(message);
        }
    }

    public List<CidadeResponse> consultarCidadesPorEstadoUf(String estadoUf) {
        try {
            log.info(String.format("Consultando a API de cidades do IBGE pelo estado: %s.", estadoUf));
            return ibgeLocalidadeClient.consultarCidadesPorEstadoUf(estadoUf);
        } catch (Exception ex) {
            var message = String.format("Erro ao tentar consultar a API de idades do IBGE pelo estado %s.", estadoUf);
            log.error(message, ex);
            throw new ValidacaoException(message);
        }
    }
}