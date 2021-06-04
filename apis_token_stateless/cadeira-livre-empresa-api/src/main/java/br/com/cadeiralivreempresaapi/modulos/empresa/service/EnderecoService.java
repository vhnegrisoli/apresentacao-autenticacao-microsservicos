package br.com.cadeiralivreempresaapi.modulos.empresa.service;

import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.empresa.dto.EnderecoRequest;
import br.com.cadeiralivreempresaapi.modulos.empresa.model.Empresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.model.Endereco;
import br.com.cadeiralivreempresaapi.modulos.empresa.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public void salvarEndereco(List<EnderecoRequest> enderecosRequest,
                               Empresa empresa) {
        enderecosRequest.forEach(enderecoRequest -> {
            validarDadosEndereco(enderecoRequest);
            enderecoRepository.save(Endereco.converterDe(enderecoRequest, empresa));
        });
    }

    public List<Endereco> buscarEnderecosDaEmpresa(Integer empresaId) {
        return enderecoRepository
            .findByEmpresaId(empresaId);
    }

    private void validarDadosEndereco(EnderecoRequest request) {
        if (isEmpty(request.getEstado())
            || isEmpty(request.getCidade())
            || isEmpty(request.getBairro())
            || isEmpty(request.getRua())
            || isEmpty(request.getNumeroRua())
            || isEmpty(request.getCep())) {
            throw new ValidacaoException("Os dados de endereço são obrigatórios: "
                .concat("estado, cidade, bairro, rua, número e CEP."));
        }
    }
}
