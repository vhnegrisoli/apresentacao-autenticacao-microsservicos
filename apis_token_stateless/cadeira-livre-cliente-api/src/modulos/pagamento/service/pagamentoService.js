import IntegracaoPagarmeClient from "../client/integracaoPagarmeClient";
import * as httpStatus from "../../../config/constantes";
import PagamentoException from "../exception/PagamentoException";

const AO_MENOS_UM_CARTAO = 1;

class PagamentoService {
  async buscarCartoesDoUsuario(req) {
    const { authorization } = req.headers;
    try {
      let dadosCartao = await IntegracaoPagarmeClient.buscarCartoesDoUsuario(
        authorization
      );
      this.validarDadosCartaoExistente(dadosCartao);
      return {
        status: dadosCartao.status,
        cartoes: dadosCartao.dados ? dadosCartao.dados : dadosCartao.message,
      };
    } catch (error) {
      return {
        status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: error.message,
      };
    }
  }

  validarDadosCartaoExistente(dadosCartao) {
    if (!dadosCartao || dadosCartao.lenght < AO_MENOS_UM_CARTAO) {
      throw new PagamentoException(
        httpStatus.BAD_REQUEST,
        "Você ainda não possui cartões registrados."
      );
    }
  }

  async salvarCartaoDoUsuario(req) {
    const {
      numeroCartao,
      dataExpiracaoCartao,
      cvvCartao,
      nomeProprietarioCartao,
    } = req.body;
    const { authorization } = req.headers;
    try {
      const cartao = {
        numeroCartao,
        dataExpiracaoCartao,
        cvvCartao,
        nomeProprietarioCartao,
      };
      this.validarDadosParaSalvarCartao(cartao);
      const cartaoSalvo = await IntegracaoPagarmeClient.salvarCartaoDoUsuario(
        cartao,
        authorization
      );
      return {
        status: cartaoSalvo.status,
        cartao: cartaoSalvo.dados ? cartaoSalvo.dados : cartaoSalvo.message,
      };
    } catch (error) {
      console.log(error);
      return {
        status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: error.message,
      };
    }
  }

  validarDadosParaSalvarCartao(cartao) {
    if (
      !cartao ||
      !cartao.numeroCartao ||
      !cartao.dataExpiracaoCartao ||
      !cartao.cvvCartao ||
      !cartao.nomeProprietarioCartao
    ) {
      throw new PagamentoException(
        httpStatus.BAD_REQUEST,
        "É necessário informar todos os dados do cartão."
      );
    }
  }
}

export default new PagamentoService();
