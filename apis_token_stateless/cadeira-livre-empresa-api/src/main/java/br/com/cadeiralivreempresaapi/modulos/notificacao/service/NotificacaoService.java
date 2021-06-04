package br.com.cadeiralivreempresaapi.modulos.notificacao.service;

import br.com.cadeiralivreempresaapi.modulos.comum.util.Constantes;
import br.com.cadeiralivreempresaapi.modulos.notificacao.dto.NotificacaoCorpoRequest;
import br.com.cadeiralivreempresaapi.modulos.notificacao.dto.NotificacaoRequest;
import br.com.cadeiralivreempresaapi.modulos.notificacao.rabbitmq.NotificacaoSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static br.com.cadeiralivreempresaapi.modulos.comum.util.Constantes.APLICACAO;

@Slf4j
@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoSender sender;
    @Autowired
    private Environment env;
    @Autowired
    @Value("${app-config.firebase.token}")
    private String aplicacaoToken;

    public void gerarDadosNotificacao(NotificacaoCorpoRequest request) {
        try {
            var notificacao = NotificacaoRequest
                .builder()
                .aplicacao(APLICACAO)
                .title(request.getTitle())
                .body(request.getBody())
                .aplicacaoToken(getAplicacaoToken())
                .userToken(request.getToken())
                .build();
            gerarLogNaAplicacao(notificacao);
            sender.produceMessage(notificacao);
        } catch (Exception ex) {
            log.error("Erro ao enviar mensagem para fila de notificações: \n"
                + ex.getMessage());
        }
    }

    private void gerarLogNaAplicacao(NotificacaoRequest request) {
        log.info("Enviando mensagem para fila de notificação.\nMensagem: \n" + request);
    }

    private String getAplicacaoToken() {
        if (Arrays.asList(env.getActiveProfiles()).contains(Constantes.TEST_PROFILE)) {
            return Constantes.TEST_TOKEN_NOTIFICACAO;
        }
        return aplicacaoToken;
    }
}
