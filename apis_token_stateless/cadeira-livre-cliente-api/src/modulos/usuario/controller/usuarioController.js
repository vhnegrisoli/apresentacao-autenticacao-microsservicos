import UsuarioService from "../service/usuarioService";

class UsuarioController {
  async salvarUsuario(req, res) {
    let usuario = await UsuarioService.salvarUsuario(req);
    return res.status(usuario.status).json(usuario);
  }

  async editarUsuario(req, res) {
    let usuario = await UsuarioService.editarUsuario(req);
    return res.status(usuario.status).json(usuario);
  }

  async buscarTodos(req, res) {
    let usuarios = await UsuarioService.buscarTodos();
    return res.status(usuarios.status).json(usuarios);
  }

  async buscarPorId(req, res) {
    let usuario = await UsuarioService.buscarPorId(req);
    return res.status(usuario.status).json(usuario);
  }
}

export default new UsuarioController();
