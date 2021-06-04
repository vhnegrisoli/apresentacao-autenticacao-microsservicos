package br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao;

import br.com.biot.integracaopagarmeapi.modulos.jwt.dto.JwtUsuarioResponse;
import br.com.biot.integracaopagarmeapi.modulos.transacao.dto.TransacaoRequest;
import br.com.biot.integracaopagarmeapi.modulos.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoClientRequest {

    private static final String PREFIXO_55 = "+55";

    @JsonProperty("api_key")
    private String apiKey;

    @JsonProperty("amount")
    private BigDecimal total;

    @JsonProperty("card_id")
    private String cartaoId;

    @JsonProperty("customer")
    private ClienteClientRequest cliente;

    @JsonProperty("billing")
    private CobrancaClientRequest cobranca;

    @JsonProperty("items")
    private List<ItemTransacaoClientRequest> itens;

    public static TransacaoClientRequest converterDe(JwtUsuarioResponse usuario,
                                                     TransacaoRequest transacaoRequest) {
        return TransacaoClientRequest
            .builder()
            .total(transacaoRequest.getTotal())
            .cartaoId(transacaoRequest.getCartaoId())
            .cliente(ClienteClientRequest.converterDe(usuario, tratarNumerosTelefone(transacaoRequest.getNumerosTelefone())))
            .cobranca(CobrancaClientRequest.converterDe(transacaoRequest.getCobranca()))
            .itens(transacaoRequest
                .getItens()
                .stream()
                .map(ItemTransacaoClientRequest::converterDe)
                .collect(Collectors.toList())
            )
            .build();
    }

    private static List<String> tratarNumerosTelefone(List<String> telefones) {
        return telefones
            .stream()
            .map(TransacaoClientRequest::tratarNumeroTelefone)
            .collect(Collectors.toList());
    }

    private static String tratarNumeroTelefone(String numeroTelefone) {
        if (!numeroTelefone.contains(PREFIXO_55)) {
            return PREFIXO_55.concat(numeroTelefone);
        }
        return numeroTelefone;
    }

    public String toJson() {
        return JsonUtil.converterJsonParaString(this);
    }
}
