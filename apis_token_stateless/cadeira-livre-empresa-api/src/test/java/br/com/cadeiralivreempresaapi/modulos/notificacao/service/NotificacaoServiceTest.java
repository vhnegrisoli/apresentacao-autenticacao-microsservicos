package br.com.cadeiralivreempresaapi.modulos.notificacao.service;

import br.com.cadeiralivreempresaapi.modulos.notificacao.dto.NotificacaoRequest;
import br.com.cadeiralivreempresaapi.modulos.notificacao.rabbitmq.NotificacaoSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static br.com.cadeiralivreempresaapi.modulos.comum.util.Constantes.APLICACAO;
import static br.com.cadeiralivreempresaapi.modulos.notificacao.mocks.NotificacaoMocks.umaNotificacaoCorpoRequest;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ActiveProfiles("test")
@Import(NotificacaoService.class)
@ExtendWith(MockitoExtension.class)
public class NotificacaoServiceTest {

    @Autowired
    private NotificacaoService service;
    @MockBean
    private NotificacaoSender sender;

    @Test
    @DisplayName("Deve enviar notificação e enviar dados quando solicitado")
    public void gerarDadosNotificacao_deveEnviarNotificacao_quandoInformarDadosCorretamente() {
        var notificacaoRequest = NotificacaoRequest
            .builder()
            .aplicacao(APLICACAO)
            .aplicacaoToken("123456")
            .body("Este é um teste de notificação")
            .title("Notificação Teste")
            .userToken("123456")
            .build();

        service.gerarDadosNotificacao(umaNotificacaoCorpoRequest());

        verify(sender, times(1)).produceMessage(notificacaoRequest);
    }
}
