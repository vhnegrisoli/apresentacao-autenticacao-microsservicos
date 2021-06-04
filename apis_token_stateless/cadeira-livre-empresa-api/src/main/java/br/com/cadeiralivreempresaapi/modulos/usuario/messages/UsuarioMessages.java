package br.com.cadeiralivreempresaapi.modulos.usuario.messages;

import br.com.cadeiralivreempresaapi.config.exception.PermissaoException;
import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.comum.response.SuccessResponseDetails;

public interface UsuarioMessages {

    ValidacaoException USUARIO_NAO_ENCONTRADO = new ValidacaoException("Usuário não encontrado.");
    ValidacaoException SEM_SESSAO = new ValidacaoException("Não há uma sessão de usuário ativa.");
    ValidacaoException EMAIL_JA_CADASTRADO = new ValidacaoException("Email já cadastrado para um usuário ativo.");
    ValidacaoException CPF_JA_CADASTRADO = new ValidacaoException("CPF já cadastrado para um usuário ativo.");
    ValidacaoException CPF_NAO_INFORMADO = new ValidacaoException("O CPF deve ser informado.");
    ValidacaoException CPF_INVALIDO = new ValidacaoException("O CPF está inválido.");
    ValidacaoException ACESSO_INVALIDO = new ValidacaoException("Usuário ou senha inválidos, tente novamente.");
    ValidacaoException DATA_NASCIMENTO_IGUAL_HOJE = new ValidacaoException("A data de nascimento não pode"
        + " ser igual à data de hoje.");
    ValidacaoException DATA_NASCIMENTO_MAIOR_HOJE = new ValidacaoException("A data de nascimento não pode ser"
        + " superior à data de hoje.");
    ValidacaoException PERMISSAO_NAO_ENCONTRADA = new ValidacaoException("A permissão não foi encontrada.");
    PermissaoException SEM_PERMISSAO_EDITAR = new PermissaoException("Você não possui permissão para alterar os "
        + "dados desse usuário.");
    PermissaoException SEM_PERMISSAO_ALTERAR_SITUACAO = new PermissaoException("Você não possui permissão para alterar "
        + "a situação desse usuário.");
    SuccessResponseDetails TOKEN_ATUALIZADO = new SuccessResponseDetails("Token de notificação atualizado com sucesso!");
    SuccessResponseDetails TOKEN_EXISTENTE = new SuccessResponseDetails("O usuário já possui esse token de notificação.");
    SuccessResponseDetails USUARIO_ALTERADO_SUCESSO = new SuccessResponseDetails("Usuário alterado com sucesso!");
    SuccessResponseDetails USUARIO_DESLOGADO_SUCESSO = new SuccessResponseDetails("O usuário foi deslogado com sucesso!");
    SuccessResponseDetails USUARIO_ALTERACAO_SITUACAO_SUCESSO = new SuccessResponseDetails("A situação do usuário"
        + " foi alterada com sucesso!");
}
