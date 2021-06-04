import EmpresaClient from "../client/empresaClient";
import CadeiraLivreService from "../../cadeiralivre/service/cadeiraLivreService";
import * as httpStatus from "../../../config/constantes";
import EmpresaException from "../exception/empresaException";

class EmpresaService {
  async buscarEmpresas(req) {
    try {
      const { authorization } = req.headers;
      const filtros = req.query;
      let token = CadeiraLivreService.tratarTokenDoRequest(authorization);
      const empresas = await EmpresaClient.buscarEmpresas(token, filtros);
      return {
        status: empresas.status,
        empresas: empresas.dados ? empresas.dados : empresas.message,
      };
    } catch (error) {
      return {
        status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: error.message,
      };
    }
  }

  async buscarEmpresaPorId(req) {
    try {
      const { authorization } = req.headers;
      const { empresaId } = req.params;
      this.validarEmpresaIdExistente(empresaId);
      let token = CadeiraLivreService.tratarTokenDoRequest(authorization);
      const empresa = await EmpresaClient.buscarEmpresaPorId(token, empresaId);
      return {
        status: empresa.status,
        empresa: empresa.dados ? empresa.dados : empresa.message,
      };
    } catch (error) {
      return {
        status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: error.message,
      };
    }
  }

  validarEmpresaIdExistente(empresaId) {
    if (!empresaId) {
      throw new EmpresaException(
        httpStatus.BAD_REQUEST,
        "É obrigatório informar o ID da empresa."
      );
    }
  }
}

export default new EmpresaService();
