package br.com.cadeiralivreempresaapi.modulos.notificacao.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NotificacaoCorpoRequestTest {

    @Test
    @DisplayName("Deve criar DTO de request de notificação ao informar título, corpo e token da aplicação")
    public void of_deveCriarDtoDeRequestDeNotificacao_quandoInformarTituloCorpoETokenDaAplicacao() {
        var request = NotificacaoCorpoRequest.of(
            "Este é um teste de notificação",
            "Notificação Teste",
            "123456"
        );
        assertThat(request).isNotNull();
        assertThat(request.getBody()).isEqualTo("Este é um teste de notificação");
        assertThat(request.getTitle()).isEqualTo("Notificação Teste");
        assertThat(request.getToken()).isEqualTo("123456");
    }
}
