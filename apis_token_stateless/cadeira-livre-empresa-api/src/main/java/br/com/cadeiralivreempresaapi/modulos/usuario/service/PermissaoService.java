package br.com.cadeiralivreempresaapi.modulos.usuario.service;

import br.com.cadeiralivreempresaapi.modulos.usuario.enums.EPermissao;
import br.com.cadeiralivreempresaapi.modulos.usuario.model.Permissao;
import br.com.cadeiralivreempresaapi.modulos.usuario.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cadeiralivreempresaapi.modulos.usuario.messages.UsuarioMessages.PERMISSAO_NAO_ENCONTRADA;

@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao buscarPorCodigo(EPermissao permissao) {
        return permissaoRepository.findByPermissao(permissao)
            .orElseThrow(() -> PERMISSAO_NAO_ENCONTRADA);
    }

    public Permissao buscarPorId(Integer id) {
        return permissaoRepository.findById(id)
            .orElseThrow(() -> PERMISSAO_NAO_ENCONTRADA);
    }
}
