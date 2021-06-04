import CadeiraLivreClient from "../client/cadeiraLivreClient";
import CadeiraLivreException from "../exception/cadeiraLivreException";
import * as httpStatus from "../../../config/constantes";

const ESPACO_BRANCO = " ";
const INDICE_TOKEN = 1;

class CadeiraLivreService {
  async buscarCadeirasLivresDisponiveis(req) {
    try {
      const { authorization } = req.headers;
      const { empresaId } = req.query;
      let token = this.tratarTokenDoRequest(authorization);
      let cadeirasLivres = await CadeiraLivreClient.buscarCadeirasLivresDisponiveis(
        token,
        empresaId
      );
      return {
        status: cadeirasLivres.status,
        cadeirasLivres: cadeirasLivres.dados
          ? cadeirasLivres.dados
          : cadeirasLivres.message,
      };
    } catch (error) {
      return {
        status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: error.message,
      };
    }
  }

  async buscarCadeirasLivresDoCliente(req) {
    try {
      const { authorization } = req.headers;
      let token = this.tratarTokenDoRequest(authorization);
      let cadeirasLivres = await CadeiraLivreClient.buscarCadeirasLivresDoCliente(
        token
      );
      return {
        status: cadeirasLivres.status,
        cadeirasLivres: cadeirasLivres.dados
          ? cadeirasLivres.dados
          : cadeirasLivres.message,
      };
    } catch (error) {
      return {
        status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: error.message,
      };
    }
  }

  async buscarCadeiraLivrePorId(req) {
    try {
      const { authorization } = req.headers;
      const { cadeiraLivreId } = req.params;
      this.validarCadeiraLivreIdExistente(cadeiraLivreId);
      let token = this.tratarTokenDoRequest(authorization);
      let cadeiraLivre = await CadeiraLivreClient.buscarCadeiraLivrePorId(
        token,
        cadeiraLivreId
      );
      this.validarPermissaoParaVisualizarCadeiraLivre(
        cadeiraLivre.dados,
        req.usuarioAutenticado
      );
      return {
        status: cadeiraLivre.status,
        cadeiraLivre: cadeiraLivre.dados
          ? cadeiraLivre.dados
          : cadeiraLivre.message,
      };
    } catch (error) {
      return {
        status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: error.message,
      };
    }
  }

  async reservarCadeiraLivre(req) {
    try {
      const { authorization } = req.headers;
      const { cadeiraLivreId, cartaoId } = req.body;
      this.validarCadeiraLivreIdExistente(cadeiraLivreId);
      this.validarCartaoIdExistente(cartaoId);
      let token = this.tratarTokenDoRequest(authorization);
      let cadeiraLivre = await CadeiraLivreClient.reservarCadeiraLivre(
        token,
        cadeiraLivreId,
        cartaoId
      );
      return {
        status: cadeiraLivre.status,
        cadeiraLivre: cadeiraLivre.dados
          ? cadeiraLivre.dados
          : cadeiraLivre.message,
      };
    } catch (error) {
      return {
        status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: error.message,
      };
    }
  }

  validarCadeiraLivreIdExistente(cadeiraLivreId) {
    if (!cadeiraLivreId) {
      throw new CadeiraLivreException(
        httpStatus.BAD_REQUEST,
        "É obrigatório informar o ID da Cadeira Livre."
      );
    }
  }

  validarCartaoIdExistente(cartaoId) {
    if (!cartaoId) {
      throw new CadeiraLivreException(
        httpStatus.BAD_REQUEST,
        "É obrigatório informar o ID do cartão."
      );
    }
  }

  tratarTokenDoRequest(authorization) {
    let token = authorization.split(ESPACO_BRANCO)[INDICE_TOKEN];
    if (!token) {
      throw new CadeiraLivreException(
        httpStatus.UNAUTHORIZED,
        "O token não foi informado."
      );
    }
    return token;
  }

  validarPermissaoParaVisualizarCadeiraLivre(cadeiraLivre, usuarioAutenticado) {
    if (
      cadeiraLivre &&
      cadeiraLivre.cliente.id &&
      cadeiraLivre.cliente.id !== usuarioAutenticado.id
    ) {
      throw new CadeiraLivreException(
        httpStatus.FORBIDDEN,
        "Você não possui permissão para "
      );
    }
  }
}

export default new CadeiraLivreService();
