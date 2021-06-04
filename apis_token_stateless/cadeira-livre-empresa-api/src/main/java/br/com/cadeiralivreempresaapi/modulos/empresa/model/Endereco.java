package br.com.cadeiralivreempresaapi.modulos.empresa.model;

import br.com.cadeiralivreempresaapi.modulos.empresa.dto.EnderecoRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ENDERECO")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @Column(name = "CIDADE", nullable = false)
    private String cidade;

    @Column(name = "BAIRRO", nullable = false)
    private String bairro;

    @Column(name = "RUA", nullable = false)
    private String rua;

    @Column(name = "NUMERO_RUA", nullable = false)
    private String numeroRua;

    @Column(name = "CEP", nullable = false)
    private String cep;

    @Column(name = "COMPLEMENTO")
    private String complemento;

    @ManyToOne
    @JoinColumn(name = "FK_EMPRESA", nullable = false)
    private Empresa empresa;

    @Column(name = "DATA_CADASTRO", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @PrePersist
    public void prePersist() {
        dataCadastro = LocalDateTime.now();
    }

    public static Endereco converterDe(EnderecoRequest enderecoRequest,
                                       Empresa empresa) {
        var endereco = new Endereco();
        BeanUtils.copyProperties(enderecoRequest, endereco);
        endereco.setEmpresa(empresa);
        return endereco;
    }
}
