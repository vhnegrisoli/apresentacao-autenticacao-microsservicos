import { Router } from "express";

import CadeiraLivreController from "../controller/cadeiraLivreContoller";

const router = Router();

router.get(
  "/api/cadeiras-livres/disponiveis",
  CadeiraLivreController.buscarCadeirasLivresDisponiveis
);
router.get(
  "/api/cadeiras-livres/cliente",
  CadeiraLivreController.buscarCadeirasLivresDoCliente
);
router.get(
  "/api/cadeiras-livres/:cadeiraLivreId",
  CadeiraLivreController.buscarCadeiraLivrePorId
);
router.post(
  "/api/cadeiras-livres/reservar",
  CadeiraLivreController.reservarCadeiraLivre
);

export default router;
