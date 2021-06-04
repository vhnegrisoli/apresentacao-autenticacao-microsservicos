package br.com.biot.integracaopagarmeapi.modulos.integracao.dto.cartao;

import br.com.biot.integracaopagarmeapi.modulos.cartao.dto.CartaoRequest;
import br.com.biot.integracaopagarmeapi.modulos.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoClientRequest {

    @JsonProperty("api_key")
    private String apiKey;

    @JsonProperty("card_expiration_date")
    private String dataExpiracaoCartao;

    @JsonProperty("card_number")
    private String numeroCartao;

    @JsonProperty("card_cvv")
    private String cvvCartao;

    @JsonProperty("card_holder_name")
    private String nomeProprietarioCartao;

    public static CartaoClientRequest converterDe(CartaoRequest request) {
        var clientRequest = new CartaoClientRequest();
        BeanUtils.copyProperties(request, clientRequest);
        return clientRequest;
    }

    public String toJson() {
        return JsonUtil.converterJsonParaString(this);
    }
}
