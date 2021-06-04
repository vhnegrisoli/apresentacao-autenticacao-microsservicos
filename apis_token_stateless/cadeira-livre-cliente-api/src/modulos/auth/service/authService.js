import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";
import { promisify } from "util";

import UsuarioRepository from "../../usuario/repository/usuarioRepository";
import RabbitMqService from "../../rabbitmq/rabbitMqService";
import * as config from "../../../config/secrets";
import * as fila from "../../../config/rabbitmq/filas";
import * as httpStatus from "../../../config/constantes";

class AuthService {
  async autenticarUsuario(req) {
    try {
      const { email, senha } = req.body;
      const usuario = await UsuarioRepository.findByEmail(email);
      if (!usuario) {
        return this.retornarUsuarioNaoExistente(email);
      }
      if (await this.isSenhaValida(senha, usuario)) {
        return this.gerarTokenJwt(usuario);
      }
      return this.retornarSenhaInvalida();
    } catch (error) {
      return { status: httpStatus.BAD_REQUEST, error };
    }
  }

  async isSenhaValida(senha, usuario) {
    return await bcrypt.compare(senha, usuario.senha);
  }

  gerarTokenJwt(usuario) {
    const { id, email, nome, cpf, telefone } = usuario;
    const token = jwt.sign(
      { id, email, nome, cpf, telefone },
      config.APPLICATION_SECRET,
      { expiresIn: "1d" }
    );
    RabbitMqService.enviarMensagemParaFila(
      { usuarioId: id, token },
      fila.AUTENTICAR_USUARIO
    );
    return { status: httpStatus.OK, token };
  }

  retornarUsuarioNaoExistente(email) {
    return {
      status: httpStatus.BAD_REQUEST,
      message: `O email ${email} não foi encontrado.`,
    };
  }

  retornarSenhaInvalida() {
    return {
      status: httpStatus.UNAUTHORIZED,
      message: "A senha está incorreta.",
    };
  }

  recuperarUsuarioAutenticado(req) {
    const { usuarioAutenticado } = req;
    if (!usuarioAutenticado) {
      return {
        status: httpStatus.FORBIDDEN,
        message: "Usuário não autenticado.",
      };
    }
    return { status: httpStatus.OK, usuarioAutenticado };
  }

  async verificarTokenValido(req) {
    const { token } = req.query;
    if (!token) {
      return {
        status: httpStatus.BAD_REQUEST,
        message: "É necessário informar um token no formato JWT.",
      };
    }
    try {
      let tokenValida = await promisify(jwt.verify)(
        token,
        config.APPLICATION_SECRET
      );
      return {
        status: httpStatus.OK,
        valida: Date.now() < tokenValida.exp * 1000,
      };
    } catch (error) {
      return { status: httpStatus.UNAUTHORIZED, valida: false };
    }
  }

  deslogarUsuario(req) {
    let { authorization } = req.headers;
    let { usuarioAutenticado } = req;
    authorization.replace("Bearer ", "");
    RabbitMqService.enviarMensagemParaFila(
      {
        usuarioId: usuarioAutenticado.id,
        token: authorization,
      },
      fila.DESLOGAR_USUARIO
    );
    return {
      status: httpStatus.OK,
      message: `O usuário ${usuarioAutenticado.nome} foi deslogado com sucesso!`,
    };
  }
}

export default new AuthService();
