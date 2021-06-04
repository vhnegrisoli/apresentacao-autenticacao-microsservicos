import mongoose from "mongoose";
import bcrypt from "bcrypt";

const Schema = mongoose.Schema;
const model = mongoose.model;

const UsuarioSchema = new Schema({
  nome: String,
  email: String,
  cpf: String,
  senha: String,
  telefone: String
});

UsuarioSchema.pre("save", async function (next) {
  let usuario = this;
  if (!usuario.isModified("senha")) {
    return next();
  }
  usuario.senha = await bcrypt.hash(usuario.senha, 10);
  return next();
});

module.exports = model("Usuario", UsuarioSchema);
