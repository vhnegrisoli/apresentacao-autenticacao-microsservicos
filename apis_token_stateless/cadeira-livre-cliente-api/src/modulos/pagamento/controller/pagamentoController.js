import PagamentoService from "../service/pagamentoService";

class PagamentoController {
  async buscarCartoesDoUsuario(req, res) {
    const dadosCartao = await PagamentoService.buscarCartoesDoUsuario(req);
    return res.status(dadosCartao.status).json(dadosCartao);
  }

  async salvarCartaoDoUsuario(req, res) {
    const cartaoSalvo = await PagamentoService.salvarCartaoDoUsuario(req);
    return res.status(cartaoSalvo.status).json(cartaoSalvo);
  }
}

export default new PagamentoController();
