import { Router } from "express";
import UsuarioController from "../controller/usuarioController";

const router = new Router();

router.post("/api/usuarios/novo", UsuarioController.salvarUsuario);
router.put("/api/usuarios/:id", UsuarioController.editarUsuario);
router.get("/api/usuarios", UsuarioController.buscarTodos);
router.get("/api/usuarios/:id", UsuarioController.buscarPorId);

export default router;
