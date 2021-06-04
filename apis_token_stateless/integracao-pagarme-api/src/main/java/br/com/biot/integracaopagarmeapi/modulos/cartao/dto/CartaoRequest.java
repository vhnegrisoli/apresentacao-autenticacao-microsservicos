package br.com.biot.integracaopagarmeapi.modulos.cartao.dto;

import br.com.biot.integracaopagarmeapi.modulos.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoRequest {

    private String dataExpiracaoCartao;
    private String numeroCartao;
    private String cvvCartao;
    private String nomeProprietarioCartao;

    public String toJson() {
        return JsonUtil.converterJsonParaString(this);
    }
}
