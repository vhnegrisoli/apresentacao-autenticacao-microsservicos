package br.com.biot.integracaopagarmeapi.modulos.cartao.dto;

import br.com.biot.integracaopagarmeapi.modulos.cartao.model.Cartao;
import br.com.biot.integracaopagarmeapi.modulos.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoResponse {

    private Integer id;

    private String cartaoId;

    private String usuarioId;

    private String bandeira;

    private String ultimosDigitos;

    private String pais;

    public static CartaoResponse converterDe(Cartao cartao) {
        var response = new CartaoResponse();
        BeanUtils.copyProperties(cartao, response);
        return response;
    }

    public String toJson() {
        return JsonUtil.converterJsonParaString(this);
    }
}
