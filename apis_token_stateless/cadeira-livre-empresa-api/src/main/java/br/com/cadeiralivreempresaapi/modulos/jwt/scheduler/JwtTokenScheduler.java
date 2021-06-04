package br.com.cadeiralivreempresaapi.modulos.jwt.scheduler;

import br.com.cadeiralivreempresaapi.modulos.jwt.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenScheduler {

    @Autowired
    private JwtService jwtService;

    @Scheduled(cron = "${app-config.scheduler.every-20-minutes}")
    public void removerTokensInvalidas() {
        log.info("Iniciando o timer de remoção das tokens inválidas.");
        jwtService.removerTokensInvalidas(false);
    }
}
