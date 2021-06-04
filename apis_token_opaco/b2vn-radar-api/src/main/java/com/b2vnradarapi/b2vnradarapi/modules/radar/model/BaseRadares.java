package com.b2vnradarapi.b2vnradarapi.modules.radar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "base_radares")
public class BaseRadares {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer gid;

    @Column(name = "id")
    private Integer id;

    @Column(name = "lote")
    private Integer lote;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "sentido")
    private String sentido;

    @Column(name = "referencia")
    private String referencia;

    @Column(name = "tipo_equip")
    private String tipoEquip;

    @Column(name = "enquadrame")
    private String enquadrame;

    @Column(name = "qtde_fxs_f")
    private Integer qtdeFxsF;

    @Column(name = "data_publi")
    private String dataPubli;

    @Column(name = "velocidade")
    private String velocidade;

    @Column(name = "latitude_l")
    private String latitudeL;

    @Column(name = "ligado")
    private Integer ligado;

    @Column(name = "data_desli")
    private String dataDesli;

    @Column(name = "motivo_des")
    private String motivoDes;

    @Column(name = "mi_style")
    private String miStyle;

    @Column(name = "mi_prinx")
    private String miPrinx;

    @Column(name = "geom")
    private String geom;

    @Column(name = "emme_gid")
    private Integer emmeId;

    @Column(name = "mdc_gid")
    private Integer mdcGid;
}
