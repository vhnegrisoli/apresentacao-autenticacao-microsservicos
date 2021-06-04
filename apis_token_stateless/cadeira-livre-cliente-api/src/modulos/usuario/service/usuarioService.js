import UsuarioRepository from "../repository/usuarioRepository";
import * as httpStatus from "../../../config/constantes";
import UsuarioException from "../exception/usuarioException";
import validarCpf from "validar-cpf";

class UsuarioService {
  async salvarUsuario(req) {
    try {
      this.validarDadosIncompletos(req);
      const { nome, cpf, email, senha, telefone } = req.body;
      await this.validarEmailJaCadastrado(email, null);
      await this.validarCpfValido(cpf);
      await this.validarCpfJaCadastrado(cpf, null);
      const usuario = await UsuarioRepository.save({
        nome,
        email,
        cpf,
        senha,
        telefone,
      });
      return this.retornarUsuarioSemSenha(usuario);
    } catch (error) {
      return {
        status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: error.message,
      };
    }
  }

  async editarUsuario(req) {
    try {
      const { id } = req.params;
      const { usuarioAutenticado } = req;
      this.validarIdNoParametro(id);
      this.validarPermissaoDoUsuario(id, usuarioAutenticado);
      this.validarDadosIncompletos(req);
      const { nome, cpf, email, senha } = req.body;
      await this.validarEmailJaCadastrado(email, id);
      await this.validarCpfValido(cpf);
      await this.validarCpfJaCadastrado(cpf, id);
      const usuario = await UsuarioRepository.save({
        id,
        nome,
        email,
        cpf,
        senha,
      });
      return this.retornarUsuarioSemSenha(usuario);
    } catch (error) {
      return {
        status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: error.message,
      };
    }
  }

  retornarUsuarioSemSenha(usuario) {
    return {
      status: httpStatus.OK,
      usuario: {
        id: usuario.id,
        nome: usuario.nome,
        email: usuario.email,
        cpf: usuario.cpf,
      },
    };
  }

  validarIdNoParametro(id) {
    if (!id) {
      throw new UsuarioException(httpStatus.BAD_REQUEST, "O ID é obrigatório");
    }
  }

  validarDadosIncompletos(req) {
    const { cpf, nome, email, senha, telefone } = req.body;
    if (!cpf || !nome || !email || !senha || !telefone) {
      throw new UsuarioException(
        httpStatus.BAD_REQUEST,
        "É necessário informar todos os campos."
      );
    }
  }

  async validarEmailJaCadastrado(email, id) {
    const usuario = await UsuarioRepository.findByEmail(email);
    if (
      (usuario && !id) ||
      (usuario && String(usuario._id) !== String(id.toString()))
    ) {
      throw new UsuarioException(
        httpStatus.BAD_REQUEST,
        "Já existe um usuário cadastrado com este e-mail."
      );
    }
  }

  validarCpfValido(cpf) {
    if (!validarCpf(cpf)) {
      throw new UsuarioException(
        httpStatus.BAD_REQUEST,
        `O CPF ${cpf} está inválido.`
      );
    }
    return true;
  }

  validarPermissaoDoUsuario(id, usuarioAutenticado) {
    if (String(usuarioAutenticado.id) !== String(id.toString())) {
      throw new UsuarioException(
        httpStatus.FORBIDDEN,
        "Você não possui permissão para visualizar este recurso."
      );
    }
    return true;
  }

  async validarCpfJaCadastrado(cpf, id) {
    const usuario = await UsuarioRepository.findByCpf(cpf);
    if (
      (usuario && !id) ||
      (usuario && String(usuario._id) !== String(id.toString()))
    ) {
      throw new UsuarioException(
        httpStatus.BAD_REQUEST,
        "Já existe um usuário cadastrado com este cpf."
      );
    }
  }

  async buscarPorId(req) {
    try {
      const { usuarioAutenticado } = req;
      const { id } = req.params;
      this.validarIdNoParametro(id);
      this.validarPermissaoDoUsuario(id, usuarioAutenticado);
      let usuario = await UsuarioRepository.findById(id);
      return this.retornarUsuarioSemSenha(usuario);
    } catch (error) {
      return {
        status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: error.message,
      };
    }
  }

  async buscarTodos() {
    let usuarios = await UsuarioRepository.findAll();
    if (usuarios.length < 1) {
      return {
        status: httpStatus.BAD_REQUEST,
        message: "Não existem usuários salvos.",
      };
    }
    let usuariosResponse = usuarios.map((usuario) => ({
      id: usuario.id,
      nome: usuario.nome,
      email: usuario.email,
      cpf: usuario.cpf,
    }));
    return { status: httpStatus.OK, usuarios: usuariosResponse };
  }
}

export default new UsuarioService();
