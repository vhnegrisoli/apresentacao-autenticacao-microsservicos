package com.b2vnradarapi.b2vnradarapi.modules.relatorios.dto;

import com.b2vnradarapi.b2vnradarapi.modules.radar.model.Contagens;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CsvContagensResponse {

    private static final String DELIMITADOR = ";";

    private Integer id;
    private Integer autuacoes;
    private String contagem;
    private LocalDateTime dataHora;
    private String localidade;
    private String placas;
    private String tipo;
    private String tipoDescricao;

    public static String getCabecalho() {
        return "id;autuacoes;contagem;dataHora;localidade;placas;tipo;tipoDescricao\n";
    }

    public static String getLinha(Contagens contagens) {
        return String.join(DELIMITADOR,
            contagens.getId().toString(),
            contagens.getAutuacoes().toString(),
            contagens.getContagem().toString(),
            contagens.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:MM:ss")),
            contagens.getLocalidade().toString(),
            contagens.getPlacas().toString(),
            contagens.getTipo().toString(),
            getTipoDescricao(contagens.getTipo()));
    }

    private static String getTipoDescricao(Integer tipo) {
        return tipo == 0 ? "MOTO" : tipo == 1 ? "PASSEIO" : tipo == 2 ? "ONIBUS" : "CAMINHAO";
    }
}
