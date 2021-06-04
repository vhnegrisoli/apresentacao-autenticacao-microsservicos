package br.com.cadeiralivreempresaapi.modulos.notificacao.mocks;

import br.com.cadeiralivreempresaapi.modulos.notificacao.dto.NotificacaoCorpoRequest;

public class NotificacaoMocks {

    public static NotificacaoCorpoRequest umaNotificacaoCorpoRequest() {
        return NotificacaoCorpoRequest
            .builder()
            .body("Este é um teste de notificação")
            .title("Notificação Teste")
            .token("123456")
            .build();
    }
}
