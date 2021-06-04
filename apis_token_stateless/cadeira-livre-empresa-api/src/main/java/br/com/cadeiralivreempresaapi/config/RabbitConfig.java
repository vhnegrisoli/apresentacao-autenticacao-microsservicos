package br.com.cadeiralivreempresaapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${app-config.topic.biot-admin}")
    private String biotAdminTopic;

    @Value("${app-config.queue.enviar-notificacao}")
    private String enviarNotificacaoMq;
    @Value("${app-config.key.enviar-notificacao}")
    private String enviarNotificacaoKey;

    @Value("${app-config.queue.autenticar-usuario}")
    private String autenticarUsuarioMq;
    @Value("${app-config.key.autenticar-usuario}")
    private String autenticarUsuarioKey;

    @Value("${app-config.queue.deslogar-usuario}")
    private String deslogarUsuarioMq;
    @Value("${app-config.key.deslogar-usuario}")
    private String deslogarUsuarioKey;

    @Value("${app-config.queue.reservar-cadeira-livre}")
    private String reservarCadeiraLivreMq;
    @Value("${app-config.key.reservar-cadeira-livre}")
    private String reservarCadeiraLivreKey;

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(biotAdminTopic);
    }

    @Bean
    Queue enviarNotificacaoMq() {
        return new Queue(enviarNotificacaoMq, false);
    }

    @Bean
    public Binding enviarNotificacaoBinding(TopicExchange exchange) {
        return BindingBuilder.bind(enviarNotificacaoMq()).to(exchange).with(enviarNotificacaoKey);
    }

    @Bean
    Queue autenticarUsuarioMq() {
        return new Queue(autenticarUsuarioMq, false);
    }

    @Bean
    public Binding autenticarUsuarioBinding(TopicExchange exchange) {
        return BindingBuilder.bind(autenticarUsuarioMq()).to(exchange).with(autenticarUsuarioKey);
    }

    @Bean
    Queue deslogarUsuarioMq() {
        return new Queue(deslogarUsuarioMq, false);
    }

    @Bean
    public Binding deslogarUsuarioBinding(TopicExchange exchange) {
        return BindingBuilder.bind(deslogarUsuarioMq()).to(exchange).with(deslogarUsuarioKey);
    }

    @Bean
    Queue reservarCadeiraLivreMq() {
        return new Queue(reservarCadeiraLivreMq, false);
    }

    @Bean
    public Binding reservarCadeiraLivreBinding(TopicExchange exchange) {
        return BindingBuilder.bind(reservarCadeiraLivreMq()).to(exchange).with(reservarCadeiraLivreKey);
    }
}
