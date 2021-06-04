package br.com.cadeiralivreempresaapi.modulos.notificacao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoRequest {

    private String userToken;
    private String title;
    private String body;
    private String aplicacao;
    private String aplicacaoToken;
}
