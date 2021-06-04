import AuthService from "../service/authService";

class AuthController {
  async autenticarUsuario(req, res) {
    let auth = await AuthService.autenticarUsuario(req);
    return res.status(auth.status).json(auth);
  }

  async verificarTokenValido(req, res) {
    let tokenValida = await AuthService.verificarTokenValido(req);
    return res.status(tokenValida.status).json(tokenValida);
  }

  recuperarUsuarioAutenticado(req, res) {
    let usuarioAutenticado = AuthService.recuperarUsuarioAutenticado(req);
    return res.status(usuarioAutenticado.status).json(usuarioAutenticado);
  }

  deslogarUsuario(req, res) {
    let usuarioLogout = AuthService.deslogarUsuario(req);
    return res.status(usuarioLogout.status).json(usuarioLogout);
  }
}

export default new AuthController();
