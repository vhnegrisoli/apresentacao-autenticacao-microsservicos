package br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoCapturaRequest {

    @JsonProperty("api_key")
    private String apiKey;

    @JsonProperty("amount")
    private BigDecimal totalPagamento;

    public static TransacaoCapturaRequest criar(String apiKey,
                                                BigDecimal totalPagamento) {
        return TransacaoCapturaRequest
            .builder()
            .apiKey(apiKey)
            .totalPagamento(totalPagamento)
            .build();
    }
}
