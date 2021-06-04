import { Router } from "express";

import EmpresaController from "../controller/empresaController";

const router = Router();

router.get("/api/empresas", EmpresaController.buscarEmpresas);
router.get("/api/empresas/:empresaId", EmpresaController.buscarEmpresaPorId);

export default router;
