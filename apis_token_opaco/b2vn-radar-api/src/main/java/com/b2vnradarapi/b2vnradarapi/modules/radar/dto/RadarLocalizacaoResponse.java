package com.b2vnradarapi.b2vnradarapi.modules.radar.dto;

import com.b2vnradarapi.b2vnradarapi.modules.radar.model.BaseRadares;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.springframework.util.ObjectUtils.isEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RadarLocalizacaoResponse {

    private static final String ESPACO_EM_BRANCO = " ";
    private static final String BARRA = "/";
    private static final String ESPACO_VAZIO = "";
    private static final String TRACO = "-";

    private Integer id;
    private List<String> codigosRadares;
    private Double latitude;
    private Double longitude;
    private String latitudeLongitude;
    private String velocidade;
    private Integer lote;

    public static RadarLocalizacaoResponse of(BaseRadares baseRadares) {
        var response = new RadarLocalizacaoResponse();
        response.setId(baseRadares.getId());
        response.setCodigosRadares(getCodigosRadares(baseRadares.getCodigo()));
        response.setLatitude(getLatitude(baseRadares.getLatitudeL()));
        response.setLongitude(getLongitude(baseRadares.getLatitudeL()));
        response.setVelocidade(baseRadares.getVelocidade());
        response.setLatitudeLongitude(baseRadares.getLatitudeL());
        response.setLote(baseRadares.getLote());
        return response;
    }

    private static List<String> getCodigosRadares(String codigosRadares) {
        if (codigosRadares.contains(ESPACO_EM_BRANCO) && codigosRadares.contains(TRACO)) {
            codigosRadares = codigosRadares.replaceAll(ESPACO_EM_BRANCO, ESPACO_VAZIO);
            return asList(codigosRadares.split(TRACO));
        }
        if (codigosRadares.contains(BARRA)) {
            return asList(codigosRadares.split(BARRA));
        }
        return singletonList(codigosRadares);
    }

    private static Double getLatitude(String dados) {
        if (!isEmpty(dados) && dados.contains(ESPACO_EM_BRANCO)) {
            dados = dados.replace("(", "").replace(")", "");
            var dadosArr = dados.split(ESPACO_EM_BRANCO);
            return isEmpty(dadosArr[0]) ? 0.0 : Double.parseDouble(dadosArr[0]);
        }
        return 0.0;
    }

    private static Double getLongitude(String dados) {
        if (!isEmpty(dados) && dados.contains(ESPACO_EM_BRANCO)) {
            dados = dados.replace("(", "").replace(")", "");
            var dadosArr = dados.split(ESPACO_EM_BRANCO);
            return isEmpty(dadosArr[1]) ? 0.0 : Double.parseDouble(dadosArr[1]);
        }
        return 0.0;
    }
}