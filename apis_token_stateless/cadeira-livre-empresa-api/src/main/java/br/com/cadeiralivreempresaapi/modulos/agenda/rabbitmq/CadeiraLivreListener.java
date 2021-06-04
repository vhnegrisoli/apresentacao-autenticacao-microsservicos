package br.com.cadeiralivreempresaapi.modulos.agenda.rabbitmq;

import br.com.cadeiralivreempresaapi.modulos.agenda.dto.cadeiralivre.CadeiraLivreReservaRequest;
import br.com.cadeiralivreempresaapi.modulos.agenda.service.CadeiraLivreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CadeiraLivreListener {

    @Autowired
    private CadeiraLivreService cadeiraLivreService;

    @RabbitListener(queues = "${app-config.queue.reservar-cadeira-livre}")
    public void reservarCadeiraLivre(CadeiraLivreReservaRequest request) {
        log.info("Reservando cadeira livre para os dados: '{}'", request.toString());
        cadeiraLivreService.reservarCadeiraLivre(request);
    }
}
