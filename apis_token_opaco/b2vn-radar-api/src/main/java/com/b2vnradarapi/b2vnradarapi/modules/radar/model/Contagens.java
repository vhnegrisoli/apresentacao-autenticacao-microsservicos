package com.b2vnradarapi.b2vnradarapi.modules.radar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "contagens", schema = "radar")
public class Contagens {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "data_e_hora")
    private LocalDateTime dataHora;

    @Column(name = "localidade")
    private Integer localidade;

    @Column(name = "tipo")
    private Integer tipo;

    @Column(name = "contagem")
    private Integer contagem;

    @Column(name = "AUTUACOES")
    private Integer autuacoes;

    @Column(name = "PLACAS")
    private Integer placas;
}
