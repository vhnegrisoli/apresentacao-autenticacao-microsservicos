import * as sender from "../../config/rabbitmq/rabbitMqSender";
import * as httpStatus from "../../config/constantes";

class RabbitMqService {
  enviarMensagemParaFila(mensagem, fila) {
    let mensagemString = JSON.stringify(mensagem);
    try {
      sender.enviarParaFila(mensagemString, fila);
    } catch (error) {
      return {
        status: httpStatus.BAD_REQUEST,
        message: `Erro ao enviar a mensagem ${mensagemString} para a fila ${fila}`,
      };
    }
  }
}

export default new RabbitMqService();
