import Usuario from "../model/usuario";

class UsuarioRepository {
  async findAll() {
    let usuarios = await Usuario.find();
    return usuarios;
  }

  async findById(id) {
    let usuario = await Usuario.findById(id);
    return usuario;
  }

  async findByEmail(email) {
    let usuario = await Usuario.findOne({ email });
    return usuario;
  }

  async findByCpf(cpf) {
    let usuario = await Usuario.findOne({ cpf });
    return usuario;
  }

  async save(usuario) {
    let usuarioCriado = await Usuario.create(usuario);
    return usuarioCriado;
  }
}

export default new UsuarioRepository();
