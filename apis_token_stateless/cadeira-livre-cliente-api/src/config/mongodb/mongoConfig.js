import mongoose from "mongoose";

import * as config from "../secrets";

export function connect() {
  mongoose.connect(config.MONGO_DB_CONNECTION, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  });
  mongoose.connection.on("connected", function () {
    console.log("Conecatdo ao MongoDB");
  });
  mongoose.connection.on("error", function () {
    console.log("Erro ao conectar ao MongoDB");
  });
}
