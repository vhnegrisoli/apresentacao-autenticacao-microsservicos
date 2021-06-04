package br.com.cadeiralivreempresaapi.modulos.transacao.dto;

import br.com.cadeiralivreempresaapi.modulos.agenda.model.Agenda;
import br.com.cadeiralivreempresaapi.modulos.empresa.model.Endereco;
import br.com.cadeiralivreempresaapi.modulos.jwt.dto.JwtUsuarioResponse;
import br.com.cadeiralivreempresaapi.modulos.transacao.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static br.com.cadeiralivreempresaapi.modulos.transacao.util.PrecoUtil.tratarValorTransacao;

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

    public static TransacaoRequest converterDe(Agenda cadeiraLivre,
                                               JwtUsuarioResponse usuario,
                                               Endereco endereco,
                                               String cartaoId) {
        return TransacaoRequest
            .builder()
            .total(BigDecimal.valueOf(cadeiraLivre.getTotalPagamento()))
            .cartaoId(cartaoId)
            .total(tratarValorTransacao(cadeiraLivre.getTotalPagamento()))
            .numerosTelefone(Collections.singletonList(usuario.getTelefone()))
            .itens(Collections.singletonList(ItemTransacaoRequest.converterDe(cadeiraLivre)))
            .cobranca(CobrancaRequest.converterDe(cadeiraLivre.getEmpresa(), endereco))
            .build();
    }

    public String toJson() {
        return JsonUtil.converterJsonParaString(this);
    }
}