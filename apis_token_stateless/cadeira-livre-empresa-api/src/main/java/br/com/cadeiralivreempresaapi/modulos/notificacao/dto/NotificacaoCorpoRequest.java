package br.com.cadeiralivreempresaapi.modulos.notificacao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoCorpoRequest {

    private String title;
    private String body;
    private String token;

    public static NotificacaoCorpoRequest of(String body, String title, String token) {
        return NotificacaoCorpoRequest
            .builder()
            .body(body)
            .title(title)
            .token(token)
            .build();
    }
}
