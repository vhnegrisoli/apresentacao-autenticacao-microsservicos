import jwt from "jsonwebtoken";
import { promisify } from "util";

import * as config from "../secrets";

const BEARER_PATTERN = "Bearer ";
const VAZIO = "";
const ENDPOINTS_PERMITIDOS = [
  "/api/auth/token",
  "/api/auth/check-token",
  "/api/usuarios/novo",
  "/swagger-ui/",
  "/swagger-ui/swagger-ui.css",
  "/swagger-ui/swagger-ui-bundle.js",
  "/swagger-ui/swagger-ui-standalone-preset.js",
  "/swagger-ui/swagger-ui-init.js",
  "/swagger-ui/swagger-ui-standalone-preset.js",
  "/swagger-ui/swagger-ui-init.js",
  "/swagger-ui/favicon-32x32.png",
  "/swagger-ui/favicon-16x16.png",
];

export default async (req, res, next) => {
  const { authorization } = req.headers;
  if (ENDPOINTS_PERMITIDOS.includes(req.url)) {
    return next();
  }
  if (!authorization) {
    return res.status(401).json({ message: "Não autenticado." });
  }
  let token = recuperarTokenDoHeader(authorization);
  try {
    await setarUsuarioDoTokenNoRequest(req, token);
    return next();
  } catch (error) {
    return res
      .status(401)
      .json({ message: "Não foi possível processar seu token." });
  }

  function recuperarTokenDoHeader(authorization) {
    if (authorization.includes(BEARER_PATTERN)) {
      return authorization.replace(BEARER_PATTERN, VAZIO);
    }
    return authorization;
  }

  async function setarUsuarioDoTokenNoRequest(req, token) {
    const dadosToken = await promisify(jwt.verify)(
      token,
      config.APPLICATION_SECRET
    );
    const { id, nome, email, cpf, telefone } = dadosToken;
    req.usuarioAutenticado = { id, nome, email, cpf };
  }
};
