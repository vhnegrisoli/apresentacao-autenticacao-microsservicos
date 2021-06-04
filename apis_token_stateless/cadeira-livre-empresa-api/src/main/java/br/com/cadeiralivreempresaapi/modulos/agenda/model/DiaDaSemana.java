package br.com.cadeiralivreempresaapi.modulos.agenda.model;

import br.com.cadeiralivreempresaapi.modulos.agenda.enums.EDiaDaSemana;
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
@Table(name = "DIA_DA_SEMANA")
public class DiaDaSemana {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "DIA", nullable = false)
    private Integer dia;

    @Column(name = "DIA_NOME", nullable = false)
    private String diaNome;

    @Enumerated(EnumType.STRING)
    @Column(name = "DIA_CODIGO", nullable = false)
    private EDiaDaSemana diaCodigo;

    public DiaDaSemana(Integer id) {
        this.id = id;
    }
}