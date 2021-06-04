import * as endpoints from "../../../config/endpoints";
import * as httpStatus from "../../../config/constantes";

import axios from "axios";

const TOKEN_PARAMETRO = "{token}";
const EMPRESA_ID_PARAMETRO = "{empresaId}";
const VAZIO = "";

class EmpresaClient {
  async buscarEmpresas(token, filtros) {
    let dados = null;
    let endpoint = this.tratarParametrosDoEndpoint(
      token,
      null,
      endpoints.ENDPOINT_BUSCAR_EMPRESAS
    );
    let parametrosEndpoint = this.tratarParametrosDeFiltro(filtros);
    endpoint = parametrosEndpoint ? endpoint + parametrosEndpoint : endpoint;
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

  async buscarEmpresaPorId(token, empresaId) {
    let dados = null;
    let endpoint = this.tratarParametrosDoEndpoint(
      token,
      empresaId,
      endpoints.ENDPOINT_BUSCAR_EMPRESA_POR_ID
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

  tratarParametrosDoEndpoint(token, empresaId, endpoint) {
    endpoint = endpoint.replace(TOKEN_PARAMETRO, token);
    endpoint = empresaId
      ? endpoint.replace(EMPRESA_ID_PARAMETRO, empresaId)
      : endpoint.replace(EMPRESA_ID_PARAMETRO, VAZIO);
    return endpoint;
  }

  tratarParametrosDeFiltro(parametros) {
    const { empresaId, cpfCnpj, nome, tipoEmpresa } = parametros;
    let parametrosEndpoint = "";
    if (empresaId) {
      parametrosEndpoint = parametrosEndpoint.concat(`&id=${empresaId}`);
    }
    if (cpfCnpj) {
      parametrosEndpoint = parametrosEndpoint.concat(`&cpfCnpj=${cpfCnpj}`);
    }
    if (nome) {
      parametrosEndpoint = parametrosEndpoint.concat(`&nome=${nome}`);
    }
    if (tipoEmpresa) {
      parametrosEndpoint = parametrosEndpoint.concat(
        `&tipoEmpresa=${tipoEmpresa}`
      );
    }
    return parametrosEndpoint;
  }
}

export default new EmpresaClient();
