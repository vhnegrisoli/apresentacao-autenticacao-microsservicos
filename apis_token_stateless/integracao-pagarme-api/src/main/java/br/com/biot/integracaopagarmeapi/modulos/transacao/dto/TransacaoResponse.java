package br.com.biot.integracaopagarmeapi.modulos.transacao.dto;

import br.com.biot.integracaopagarmeapi.modulos.transacao.model.Transacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static br.com.biot.integracaopagarmeapi.modulos.util.Constantes.DATA_HORA_BR_FORMATO;
import static br.com.biot.integracaopagarmeapi.modulos.util.NumeroUtil.tratarValorPagamento;
import static org.springframework.util.ObjectUtils.isEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoResponse {

    private Integer transacaoId;
    private Long transacaoPagarmeId;
    private String transacaoPagarmeStatus;
    private String transacaoStatus;
    @JsonFormat(pattern = DATA_HORA_BR_FORMATO)
    private LocalDateTime horarioTransacao;
    private String transacaoCartaoId;
    private String transacaoUsuarioId;
    private String transacaoCartaoUltimosDigitos;
    private String transacaoCartaoBandeira;
    private BigDecimal valorPagamento;

    public static TransacaoResponse converterDe(Transacao transacao) {
        return TransacaoResponse
            .builder()
            .transacaoId(transacao.getId())
            .transacaoPagarmeId(transacao.getTransacaoId())
            .transacaoPagarmeStatus(transacao.getSituacaoTransacao())
            .transacaoStatus(!isEmpty(transacao.getTransacaoStatus())
                ? transacao.getTransacaoStatus().name()
                : Strings.EMPTY)
            .horarioTransacao(transacao.getDataCadastro())
            .transacaoUsuarioId(transacao.getUsuarioId())
            .transacaoCartaoId(transacao.getCartao().getCartaoId())
            .transacaoCartaoUltimosDigitos(transacao.getCartao().getUltimosDigitos())
            .transacaoCartaoBandeira(transacao.getCartao().getBandeira())
            .valorPagamento(tratarValorPagamento(transacao.getValorPagamento()))
            .build();
    }
}
