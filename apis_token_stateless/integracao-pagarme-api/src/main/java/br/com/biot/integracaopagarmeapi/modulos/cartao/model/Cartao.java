package br.com.biot.integracaopagarmeapi.modulos.cartao.model;

import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.cartao.CartaoClientResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CARTAO")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "CARTAO_ID", nullable = false)
    private String cartaoId;

    @Column(name = "USUARIO_ID", nullable = false)
    private String usuarioId;

    @Column(name = "ULTIMOS_DIGITOS ", nullable = false)
    private String ultimosDigitos;

    @Column(name = "BANDEIRA ", nullable = false)
    private String bandeira;

    @Column(name = "PAIS ", nullable = false)
    private String pais;

    @Column(name = "DATA_CADASTRO", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "DATA_ATUALIZACAO", nullable = false)
    private LocalDateTime dataAtualizacao;

    @PrePersist
    public void prePersist() {
        dataCadastro = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    public static Cartao converterDe(CartaoClientResponse cartaoResponse,
                                     String usuarioId) {
        var cartao = new Cartao();
        BeanUtils.copyProperties(cartaoResponse, cartao);
        cartao.setCartaoId(cartaoResponse.getId());
        cartao.setUsuarioId(usuarioId);
        return cartao;
    }
}