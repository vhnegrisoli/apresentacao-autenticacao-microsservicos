package br.com.biot.integracaopagarmeapi.modulos.transacao.dto;

import br.com.biot.integracaopagarmeapi.modulos.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoRequest {

    private BigDecimal total;
    private String cartaoId;
    private List<String> numerosTelefone;
    private CobrancaRequest cobranca;
    private List<ItemTransacaoRequest> itens;

    public String toJson() {
        return JsonUtil.converterJsonParaString(this);
    }
}
