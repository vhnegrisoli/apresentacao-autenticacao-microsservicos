package br.com.cadeiralivreempresaapi.modulos.transacao.dto;

import br.com.cadeiralivreempresaapi.modulos.agenda.model.Agenda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static br.com.cadeiralivreempresaapi.modulos.transacao.util.PrecoUtil.tratarValorTransacao;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemTransacaoRequest {

    private static final Long QUANTIDADE_TOTAL = 1L;

    private String id;
    private String titulo;
    private BigDecimal precoUnitario;
    private Long quantidade;

    public static ItemTransacaoRequest converterDe(Agenda cadeiraLivre) {
        return ItemTransacaoRequest
            .builder()
            .id(String.valueOf(cadeiraLivre.getId()))
            .titulo(cadeiraLivre.getTipoAgenda().name())
            .precoUnitario(tratarValorTransacao(cadeiraLivre.getTotalPagamento()))
            .quantidade(QUANTIDADE_TOTAL)
            .build();
    }
}