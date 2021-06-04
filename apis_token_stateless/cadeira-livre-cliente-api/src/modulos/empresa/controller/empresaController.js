import EmpresaService from "../service/empresaService";

class EmpresaController {
  async buscarEmpresas(req, res) {
    let empresas = await EmpresaService.buscarEmpresas(req);
    return res.status(empresas.status).json(empresas);
  }

  async buscarEmpresaPorId(req, res) {
    let empresa = await EmpresaService.buscarEmpresaPorId(req);
    return res.status(empresa.status).json(empresa);
  }
}
export default new EmpresaController();
