package br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao;

import br.com.biot.integracaopagarmeapi.modulos.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class TransacaoClientResponse {

    private Long id;

    @JsonProperty("object")
    private String objeto;

    @JsonProperty("status")
    private String status;

    @JsonProperty("authorization_code")
    private String codigoAutorizacao;

    @JsonProperty("acquirer_id")
    private String adquirenteId;

    @JsonProperty("acquirer_name")
    private String adquirenteNome;

    @JsonProperty("amount")
    private BigDecimal total;

    @JsonProperty("authorized_amount")
    private BigDecimal totalAutorizado;

    @JsonProperty("paid_amount")
    private BigDecimal totalPago;

    @JsonProperty("payment_method")
    private String metodoPagamento;

    @JsonProperty("refuse_reason")
    private String motivoRecusa;

    @JsonProperty("status_reason")
    private String statusRecusa;

    @JsonProperty("capture_method")
    private String metodoCaptura;

    @JsonIgnore
    public String getIdStr() {
        return String.valueOf(id);
    }

    public String toJson() {
        return JsonUtil.converterJsonParaString(this);
    }
}
