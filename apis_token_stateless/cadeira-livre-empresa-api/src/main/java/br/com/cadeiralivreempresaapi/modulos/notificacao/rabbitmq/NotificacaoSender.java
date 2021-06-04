package br.com.cadeiralivreempresaapi.modulos.notificacao.rabbitmq;

import br.com.cadeiralivreempresaapi.modulos.notificacao.dto.NotificacaoRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoSender {

    @Autowired
    private RabbitTemplate template;

    @Value("${app-config.queue.enviar-notificacao}")
    private String enviarNotificacaoMq;

    public void produceMessage(NotificacaoRequest request) {
        template.convertAndSend(enviarNotificacaoMq, request);
    }
}
