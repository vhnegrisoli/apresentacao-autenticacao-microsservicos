package br.com.b2vnauthapi.b2vnauthapi.modules.log.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "LOG")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "usuario_id")
    @NotNull
    private Integer usuarioId;

    @Column(name = "usuario_nome")
    @NotNull
    private String usuarioNome;

    @Column(name = "usuario_email")
    @NotNull
    private String usuarioEmail;

    @Column(name = "usuario_permissao")
    @NotNull
    private String usuarioPermissao;

    @Column(name = "usuario_descricao")
    @NotNull
    private String usuarioDescricao;

    @Column(name = "servico_nome")
    @NotNull
    private String servicoNome;

    @Column(name = "servico_descricao")
    @NotNull
    private String servicoDescricao;

    @Column(name = "url_acessada")
    @NotNull
    private String urlAcessada;

    @Column(name = "metodo")
    @NotNull
    private String metodo;

    @Column(name = "tipo_operacao")
    @NotNull
    private String tipoOperacao;

    @Column(name = "data_acesso")
    @NotNull
    private LocalDateTime dataAcesso;
}
