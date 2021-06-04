package br.com.cadeiralivreempresaapi.modulos.agenda.scheduler;

import br.com.cadeiralivreempresaapi.modulos.agenda.service.CadeiraLivreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CadeiraLivreScheduler {

    @Autowired
    private CadeiraLivreService cadeiraLivreService;

    @Scheduled(cron = "${app-config.scheduler.every-minute}")
    public void indisponibilizarCadeirasLivresExpiradas() {
        log.info("Iniciando scheduler de indisponibilização de cadeiras livres disponíveis com tempo expirado.");
        cadeiraLivreService.indisponibilizarCadeirasLivresExpiradas(false);
    }
}