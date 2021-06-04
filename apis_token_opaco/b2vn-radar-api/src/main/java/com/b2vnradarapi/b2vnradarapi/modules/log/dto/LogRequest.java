package com.b2vnradarapi.b2vnradarapi.modules.log.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LogRequest {

    private Integer usuarioId;
    private String usuarioNome;
    private String usuarioEmail;
    private String usuarioPermissao;
    private String usuarioDescricao;
    private String servicoNome;
    private String servicoDescricao;
    private String urlAcessada;
    private String metodo;
    private String tipoOperacao;
    private LocalDateTime dataAcesso;

}
