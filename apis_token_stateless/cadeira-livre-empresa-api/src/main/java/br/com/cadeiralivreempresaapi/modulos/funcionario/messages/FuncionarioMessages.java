package br.com.cadeiralivreempresaapi.modulos.funcionario.messages;

import br.com.cadeiralivreempresaapi.config.exception.PermissaoException;
import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.comum.response.SuccessResponseDetails;

public interface FuncionarioMessages {

    ValidacaoException FUNCIONARIO_NAO_ENCONTRADO = new ValidacaoException("O funcionário não foi encontrado.");
    PermissaoException FUNCIONARIO_USUARIO_SEM_PERMISSAO = new PermissaoException("Usuário sem permissão para visualizar"
        + " funcionários desta empresa.");
    SuccessResponseDetails FUNCIONARIO_CRIADO_SUCESSO = new SuccessResponseDetails("Funcionário inserido com sucesso!");
}
