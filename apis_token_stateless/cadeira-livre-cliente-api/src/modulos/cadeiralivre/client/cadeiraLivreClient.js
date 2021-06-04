import * as endpoints from "../../../config/endpoints";
import * as httpStatus from "../../../config/constantes";

import axios from "axios";

const TOKEN_PARAMETRO = "{token}";
const EMPRESA_ID_PARAMETRO = "{empresaId}";
const CADEIRA_LIVRE_ID_PARAMETRO = "{cadeiraLivreId}";
const VAZIO = "";

class CadeiraLivreClient {
  async buscarCadeirasLivresDisponiveis(token, empresaId) {
    let dados = null;
    const endpoint = this.tratarParametrosDoEndpoint(
      token,
      empresaId,
      endpoints.ENDPOINT_CADEIRAS_LIVRES_DISPONIVEIS,
      null
    );
    await axios
      .get(endpoint)
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
    return dados;
  }

  async buscarCadeirasLivresDoCliente(token) {
    let endpoint = this.tratarParametrosDoEndpoint(
      token,
      null,
      endpoints.ENDPOINT_CADEIRAS_LIVRES_DO_CLIENTE,
      null
    );
    let dados = null;
    await axios
      .get(endpoint)
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
    return dados;
  }

  async buscarCadeiraLivrePorId(token, cadeiraLivreId) {
    let endpoint = this.tratarParametrosDoEndpoint(
      token,
      null,
      endpoints.ENDPOINT_CADEIRAS_LIVRES_POR_ID,
      cadeiraLivreId
    );
    let dados = null;
    await axios
      .get(endpoint)
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
    return dados;
  }

  async reservarCadeiraLivre(token, cadeiraLivreId, cartaoId) {
    let endpoint = this.tratarParametrosDoEndpoint(
      token,
      null,
      endpoints.ENDPOINT_RESERVAR_CADEIRA_LIVRE,
      cadeiraLivreId
    );
    let dados = null;
    await axios
      .post(`${endpoint}&cartaoId=${cartaoId}`)
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
    return dados;
  }

  tratarParametrosDoEndpoint(token, empresaId, endpoint, cadeiraLivreId) {
    endpoint = endpoint.replace(TOKEN_PARAMETRO, token);
    endpoint = empresaId
      ? endpoint.replace(EMPRESA_ID_PARAMETRO, empresaId)
      : endpoint.replace(EMPRESA_ID_PARAMETRO, VAZIO);
    endpoint = cadeiraLivreId
      ? endpoint.replace(CADEIRA_LIVRE_ID_PARAMETRO, cadeiraLivreId)
      : endpoint.replace(CADEIRA_LIVRE_ID_PARAMETRO, VAZIO);
    return endpoint;
  }
}

export default new CadeiraLivreClient();
