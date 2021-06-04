package br.com.cadeiralivreempresaapi.modulos.agenda.model;

import br.com.cadeiralivreempresaapi.modulos.agenda.dto.servico.ServicoRequest;
import br.com.cadeiralivreempresaapi.modulos.empresa.model.Empresa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SERVICO")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "DESCRICAO", nullable = false, length = 120)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "FK_EMPRESA", nullable = false)
    private Empresa empresa;

    @Column(name = "PRECO", nullable = false)
    private Double preco;

    public Servico(Integer id) {
        this.id = id;
    }

    public static Servico of(ServicoRequest request) {
        return Servico
            .builder()
            .descricao(request.getDescricao())
            .empresa(new Empresa(request.getEmpresaId()))
            .preco(request.getPreco())
            .build();
    }
}
