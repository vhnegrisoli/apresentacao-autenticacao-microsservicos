package br.com.biot.integracaopagarmeapi.modulos.integracao.dto.cartao;

import br.com.biot.integracaopagarmeapi.modulos.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoClientResponse {

    private String id;

    @JsonProperty("brand")
    private String bandeira;

    @JsonProperty("last_digits")
    private String ultimosDigitos;

    @JsonProperty("country")
    private String pais;

    public String toJson() {
        return JsonUtil.converterJsonParaString(this);
    }
}
