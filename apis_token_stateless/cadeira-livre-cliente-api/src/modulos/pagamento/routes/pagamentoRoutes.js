import PagamentoController from "../controller/pagamentoController";

import { Router } from "express";

const router = new Router();

router.get(
  "/api/pagamento/cartoes",
  PagamentoController.buscarCartoesDoUsuario
);
router.post("/api/pagamento/cartao", PagamentoController.salvarCartaoDoUsuario);

export default router;
