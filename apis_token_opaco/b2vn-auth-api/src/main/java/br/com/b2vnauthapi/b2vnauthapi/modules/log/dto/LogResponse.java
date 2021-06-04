package br.com.b2vnauthapi.b2vnauthapi.modules.log.dto;

import br.com.b2vnauthapi.b2vnauthapi.modules.log.model.Log;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogResponse {

    private Integer usuarioId;
    private String usuarioNome;
    private String usuarioEmail;
    private String usuarioDescricao;
    private String servicoDescricao;
    private String urlAcessada;
    private String metodo;
    private String tipoOperacao;
    private LocalDateTime dataAcesso;

    public static LogResponse of(Log log) {
        var response = new LogResponse();
        BeanUtils.copyProperties(log, response);
        return response;
    }
}
