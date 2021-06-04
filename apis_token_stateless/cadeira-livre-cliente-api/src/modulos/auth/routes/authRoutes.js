import { Router } from "express";
import AuthController from "../controller/authController";

const router = new Router();

router.post("/api/auth/token", AuthController.autenticarUsuario);
router.get("/api/auth/check-token", AuthController.verificarTokenValido);
router.get(
  "/api/auth/usuario-autenticado",
  AuthController.recuperarUsuarioAutenticado
);
router.post("/api/auth/logout", AuthController.deslogarUsuario);
export default router;
