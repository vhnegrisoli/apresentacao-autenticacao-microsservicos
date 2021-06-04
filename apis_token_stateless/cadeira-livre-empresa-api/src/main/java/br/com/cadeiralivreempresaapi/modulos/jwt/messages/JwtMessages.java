package br.com.cadeiralivreempresaapi.modulos.jwt.messages;

import br.com.cadeiralivreempresaapi.config.exception.PermissaoException;
import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;

public interface JwtMessages {

    ValidacaoException ERRO_DESCRIPTOGRAFAR_TOKEN =
        new ValidacaoException("Erro ao tentar descriptografar o token.");
    ValidacaoException TOKEN_INVALIDA =
        new ValidacaoException("O token está inválido.");
    PermissaoException USUARIO_NAO_AUTENTICADO =
        new PermissaoException("O usuário não está autenticado.");
}