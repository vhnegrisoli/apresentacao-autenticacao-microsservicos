package br.com.cadeiralivreempresaapi.modulos.usuario.model;

import br.com.cadeiralivreempresaapi.modulos.usuario.enums.EPermissao;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "PERMISSAO")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "PERMISSAO")
    @NotNull
    @Enumerated(EnumType.STRING)
    private EPermissao permissao;

    @Column(name = "DESCRICAO")
    @NotNull
    private String descricao;
}
