import CadeiraLivreService from "../service/cadeiraLivreService";

class CadeiraLivreController {
  async buscarCadeirasLivresDisponiveis(req, res) {
    let cadeirasLivres = await CadeiraLivreService.buscarCadeirasLivresDisponiveis(
      req
    );
    return res.status(cadeirasLivres.status).json(cadeirasLivres);
  }

  async buscarCadeirasLivresDoCliente(req, res) {
    let cadeirasLivres = await CadeiraLivreService.buscarCadeirasLivresDoCliente(
      req
    );
    return res.status(cadeirasLivres.status).json(cadeirasLivres);
  }

  async buscarCadeiraLivrePorId(req, res) {
    let cadeiraLivre = await CadeiraLivreService.buscarCadeiraLivrePorId(req);
    return res.status(cadeiraLivre.status).json(cadeiraLivre);
  }

  async reservarCadeiraLivre(req, res) {
    let cadeiraLivre = await CadeiraLivreService.reservarCadeiraLivre(req);
    return res.status(cadeiraLivre.status).json(cadeiraLivre);
  }
}

export default new CadeiraLivreController();
