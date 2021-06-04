package br.com.biot.integracaopagarmeapi.modulos.transacao.scheduler;

import br.com.biot.integracaopagarmeapi.modulos.transacao.service.TransacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static br.com.biot.integracaopagarmeapi.modulos.util.Constantes.DATA_HORA_BR_FORMATO;

@Slf4j
@Component
public class TransacaoCapturaTimer {

    @Autowired
    private TransacaoService transacaoService;

    @Scheduled(cron = "${app-config.schedulers.transacoes.atualizar-transacoes-cada-20-minutos}")
    public void capturarTransacoesAutorizadas() {
        var dataHoratual = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATA_HORA_BR_FORMATO));
        log.info(String.format("Iniciando timer para atualização de transações autorizadas às %s.", dataHoratual));
        transacaoService.capturarTransacoesAutorizadas();
        log.info("Finalizando timer para atualização de transações autorizadas.");
    }
}
