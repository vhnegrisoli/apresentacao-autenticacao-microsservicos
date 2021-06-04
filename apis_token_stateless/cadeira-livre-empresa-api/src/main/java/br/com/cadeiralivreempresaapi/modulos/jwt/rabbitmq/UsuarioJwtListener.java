package br.com.cadeiralivreempresaapi.modulos.jwt.rabbitmq;

import br.com.cadeiralivreempresaapi.modulos.jwt.dto.UsuarioTokenResponse;
import br.com.cadeiralivreempresaapi.modulos.jwt.service.JwtService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioJwtListener {

    @Autowired
    private JwtService jwtService;

    @RabbitListener(queues = "${app-config.queue.autenticar-usuario}")
    public void autenticarUsuario(UsuarioTokenResponse response) {
        jwtService.salvarUsuarioDoToken(response, true);
    }

    @RabbitListener(queues = "${app-config.queue.deslogar-usuario}")
    public void deslogarUsuario(UsuarioTokenResponse response) {
        jwtService.salvarUsuarioDoToken(response, false);
    }
}
