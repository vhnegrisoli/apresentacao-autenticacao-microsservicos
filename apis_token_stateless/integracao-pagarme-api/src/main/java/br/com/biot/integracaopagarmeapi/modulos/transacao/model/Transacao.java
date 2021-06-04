package br.com.biot.integracaopagarmeapi.modulos.transacao.model;

import br.com.biot.integracaopagarmeapi.modulos.cartao.model.Cartao;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao.TransacaoClientResponse;
import br.com.biot.integracaopagarmeapi.modulos.jwt.dto.JwtUsuarioResponse;
import br.com.biot.integracaopagarmeapi.modulos.transacao.dto.TransacaoRequest;
import br.com.biot.integracaopagarmeapi.modulos.transacao.enums.TransacaoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TRANSACAO")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "CARTAO_ID", nullable = false)
    private Cartao cartao;

    @Column(name = "TRANSACAO_ID", nullable = false)
    private Long transacaoId;

    @Column(name = "SITUACAO_TRANSACAO", nullable = false)
    private String situacaoTransacao;

    @Column(name = "TRANSACAO_STATUS")
    @Enumerated(EnumType.STRING)
    private TransacaoStatus transacaoStatus;

    @Column(name = "DATA_CADASTRO", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "USUARIO_ID", nullable = false)
    private String usuarioId;

    @Column(name = "EMPRESA_ID")
    private Integer empresaId;

    @Column(name = "EMPRESA_NOME")
    private String empresaNome;

    @Column(name = "EMPRESA_CPF_CNPJ")
    private String empresaCpfCnpj;

    @Column(name = "VALOR_PAGAMENTO")
    private BigDecimal valorPagamento;

    @Transient
    public boolean isPaga() {
        return TransacaoStatus.PAGA.equals(transacaoStatus)
            || TransacaoStatus.PAGA.getStatusPagarme().equals(situacaoTransacao);
    }

    @Transient
    public boolean isAutorizada() {
        return TransacaoStatus.AUTORIZADA.equals(transacaoStatus)
            || TransacaoStatus.AUTORIZADA.getStatusPagarme().equals(situacaoTransacao);
    }

    @Transient
    public boolean isAnalise() {
        return TransacaoStatus.ANALISANDO.equals(transacaoStatus)
            || TransacaoStatus.ANALISANDO.getStatusPagarme().equals(situacaoTransacao);
    }

    @Transient
    public boolean isRecusada() {
        return TransacaoStatus.RECUSADA.equals(transacaoStatus)
            || TransacaoStatus.RECUSADA.getStatusPagarme().equals(situacaoTransacao)
            || !TransacaoStatus.possuiStatusValidos(situacaoTransacao);
    }

    @PrePersist
    public void prePersist() {
        dataCadastro = LocalDateTime.now();
    }

    public static Transacao converterDe(JwtUsuarioResponse usuario,
                                        TransacaoRequest transacaoRequest,
                                        TransacaoClientResponse transacaoResponse,
                                        Cartao cartao) {
        return Transacao
            .builder()
            .transacaoId(transacaoResponse.getId())
            .transacaoStatus(definirTransacaoStatus(transacaoResponse.getStatus()))
            .situacaoTransacao(transacaoResponse.getStatus())
            .usuarioId(usuario.getId())
            .cartao(cartao)
            .empresaId(transacaoRequest.getCobranca().getId())
            .empresaNome(transacaoRequest.getCobranca().getNome())
            .empresaCpfCnpj(transacaoRequest.getCobranca().getCpfCnpj())
            .valorPagamento(transacaoResponse.getTotalPago())
            .build();
    }

    public static TransacaoStatus definirTransacaoStatus(String status) {
        try {
            return Arrays
                .stream(TransacaoStatus.values())
                .filter(transacaoStatus -> transacaoStatus.getStatusPagarme().equals(status))
                .findFirst()
                .orElse(null);
        } catch (Exception ex) {
            return null;
        }
    }
}
