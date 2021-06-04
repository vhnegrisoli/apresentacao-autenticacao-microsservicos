package br.com.cadeiralivreempresaapi.modulos.empresa.messages;

import br.com.cadeiralivreempresaapi.config.exception.PermissaoException;
import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.comum.response.SuccessResponseDetails;

public interface EmpresaMessages {

    ValidacaoException EMPRESA_NAO_ENCONTRADA = new ValidacaoException("A empresa não foi encontrada.");
    ValidacaoException RAZAO_SOCIAL_EXISTENTE = new ValidacaoException("Razão social já existente para"
        + " outra empresa.");
    ValidacaoException CNPJ_EXISTENTE = new ValidacaoException("Cnpj já existente para outra empresa.");
    ValidacaoException EMPRESA_INATIVA = new ValidacaoException("Não é possível cadastrar um funcionário para uma "
        + "empresa inativa.");
    ValidacaoException USUARIO_NAO_PROPRIETARIO = new ValidacaoException("Para salvar uma empresa, o usuário deve ser "
        + "um proprietário.");
    ValidacaoException EMPRESA_SEM_ENDERECO = new ValidacaoException("É necessário informar o endereço da empresa.");
    PermissaoException EMPRESA_USUARIO_SEM_PERMISSAO = new PermissaoException("Usuário sem permissão para visualizar "
        + "essa empresa.");
    SuccessResponseDetails EMPRESA_CRIADA_SUCESSO = new SuccessResponseDetails("A empresa foi criada com sucesso!");
    SuccessResponseDetails EMPRESA_ALTERADA_SUCESSO = new SuccessResponseDetails("A empresa foi alterada com sucesso!");
    SuccessResponseDetails PROPRIETARIO_CRIADO_SUCESSO = new SuccessResponseDetails("Proprietário inserido com sucesso!");
    SuccessResponseDetails SOCIO_CRIADO_SUCESSO = new SuccessResponseDetails("Sócio inserido com sucesso!");
    SuccessResponseDetails EMPRESA_SITUACAO_ALTERADA_SUCESSO = new SuccessResponseDetails("A situação da empresa "
        + "foi alterada com sucesso!");
}
