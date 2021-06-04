import axios from "axios";
import * as url from "../../../config/secrets";
import * as httpStatus from "../../../config/constantes";

class IntegracaoPagarmeClient {
  async buscarCartoesDoUsuario(token) {
    let headers = this.criarHeaders(token);
    let dados = null;
    console.info(
      `Chamando endpoint para buscar cartões na API de integração da Pagar.me para o token: ${token}`
    );
    await axios
      .get(`${url.INTEGRACAO_PAGARME_BASE_URI}/api/cartoes/usuario`, headers)
      .then((res) => {
        dados = { status: httpStatus.OK, dados: res.data };
      })
      .catch((error) => {
        dados = {
          status: error.response.status,
          message: error.response.data.message
            ? error.response.data.message
            : error.message,
        };
      });
    console.info(
      `Retorno da consulta de cartões da API de Integração do Pagar.me: ${JSON.stringify(
        dados
      )}`
    );
    return dados;
  }

  async salvarCartaoDoUsuario(cartao, token) {
    let headers = this.criarHeaders(token);
    let dados = null;
    console.info(
      `Chamando endpoint para buscar salvar na API de integração da Pagar.me para os dados: ${JSON.stringify(
        cartao
      )} e token: ${token}`
    );
    await axios
      .post(`${url.INTEGRACAO_PAGARME_BASE_URI}/api/cartoes`, cartao, headers)
      .then((res) => {
        dados = { status: httpStatus.OK, dados: res.data };
      })
      .catch((error) => {
        dados = {
          status: error.response.status,
          message: error.response.data.message
            ? error.response.data.message
            : error.message,
        };
      });
    console.info(
      `Retorno do cadastro de cartões da API de Integração do Pagar.me: ${JSON.stringify(
        dados
      )}`
    );
    return dados;
  }

  criarHeaders(token) {
    let bearerToken =
      token && token.includes("Bearer") ? token : `Bearer ${token}`;
    return {
      headers: {
        Authorization: bearerToken,
      },
    };
  }
}

export default new IntegracaoPagarmeClient();
