import express from "express";
import cors from "cors";
import cookieParser from "cookie-parser";

import * as mongodb from "./src/config/mongodb/mongoConfig";
import * as rabbitMq from "./src/config/rabbitmq/rabbitMqSender";
import usuario from "./src/modulos/usuario/routes/usuarioRoutes";
import autenticacao from "./src/modulos/auth/routes/authRoutes";
import cadeiraLivre from "./src/modulos/cadeiralivre/routes/cadeiraLivreRoutes";
import empresa from "./src/modulos/empresa/routes/empresaRoutes";
import pagamento from "./src/modulos/pagamento/routes/pagamentoRoutes";
import checkToken from "./src/config/auth/checkToken";
import swaggerUi from "swagger-ui-express";
import openApiDocumentation from "./openApiDocumentation.js";

const env = process.env;
const app = express();
const PORT = env.PORT || 8096;

app.use(express.json());
app.use(cors());
app.use(cookieParser());
app.use(
  "/swagger-ui.html",
  swaggerUi.serve,
  swaggerUi.setup(openApiDocumentation)
);

app.get("/", (req, res) => {
  return res.redirect("/swagger-ui.html");
});

app.use(checkToken);

mongodb.connect();
rabbitMq.inicializarRabbitMQ();

app.use(pagamento);
app.use(autenticacao);
app.use(usuario);
app.use(cadeiraLivre);
app.use(empresa);

app.listen(PORT, () => {
  console.log(
    `Aplicação iniciada na porta ${PORT} no ambiente: ${env.NODE_ENV}`
  );
});
