package com.b2vnradarapi.b2vnradarapi.modules.relatorios.dto;

import com.b2vnradarapi.b2vnradarapi.modules.radar.model.BaseRadares;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("MethodLength")
public class CsvRadaresResponse {

    private static final String DELIMITADOR = ";";

    private Integer gid;
    private Integer id;
    private Integer lote;
    private String codigo;
    private String endereco;
    private String sentido;
    private String referencia;
    private String tipoEquip;
    private String enquadrame;
    private Integer qtdeFxsF;
    private String dataPubli;
    private String velocidade;
    private String latitudeL;
    private Integer ligado;
    private String dataDesli;
    private String motivoDes;
    private String miStyle;
    private String miPrinx;
    private String geom;
    private Integer emmeId;
    private Integer mdcGid;

    public static String getCabecalho() {
        return "gid;id;lote;codigo;endereco;sentido;referencia;tipoEquip;enquadrame;qtdeFxsF;dataPubli;velocidade;"
            + "latitudeL;ligado;dataDesli;motivoDes;miStyle;miPrinx;geom;emmeId;mdcGid\n";
    }

    public static String getLinha(BaseRadares baseRadares) {
        return String.join(DELIMITADOR,
            baseRadares.getGid().toString(),
            baseRadares.getId().toString(),
            baseRadares.getLote().toString(),
            baseRadares.getCodigo(),
            baseRadares.getEndereco(),
            baseRadares.getSentido(),
            baseRadares.getReferencia(),
            baseRadares.getTipoEquip(),
            baseRadares.getEnquadrame(),
            baseRadares.getQtdeFxsF().toString(),
            baseRadares.getDataPubli(),
            baseRadares.getVelocidade(),
            baseRadares.getLatitudeL(),
            baseRadares.getLigado().toString(),
            baseRadares.getDataDesli(),
            baseRadares.getMotivoDes(),
            baseRadares.getMiStyle(),
            baseRadares.getMiPrinx(),
            baseRadares.getGeom(),
            baseRadares.getEmmeId().toString(),
            baseRadares.getMdcGid().toString());
    }
}
