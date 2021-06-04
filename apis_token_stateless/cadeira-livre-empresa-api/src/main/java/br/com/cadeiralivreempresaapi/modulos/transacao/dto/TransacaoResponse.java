package br.com.cadeiralivreempresaapi.modulos.transacao.dto;

import br.com.cadeiralivreempresaapi.modulos.transacao.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoResponse {

    private Integer transacaoId;
    private Long transacaoPagarmeId;
    private String transacaoPagarmeStatus;
    private String transacaoStatus;
    private String horarioTransacao;
    private String transacaoCartaoId;
    private String transacaoUsuarioId;
    private String transacaoCartaoUltimosDigitos;
    private String transacaoCartaoBandeira;

    public String toJson() {
        return JsonUtil.converterJsonParaString(this);
    }
}