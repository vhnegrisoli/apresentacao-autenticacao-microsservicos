package com.b2vnradarapi.b2vnradarapi.modules.trajetos.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SuppressWarnings("PMD.AvoidDecimalLiteralsInBigDecimalConstructor")
public class TrajetoUtils {

    private static final Integer UM = 1;
    private static final Integer DOIS = 2;
    private static final Integer CENTO_OITENTA_GRAUS = 180;
    private static final Integer CONVERTER_METRO = 1000;
    private static final Integer RADIANOS_TERRA = 6371;

    public static BigDecimal calcularDistancia(Double latitude1, Double longitude1, Double latitude2, Double longitude2) {
        var distanciaLatitude = grausParaRadianos((double) latitude2 - latitude1);
        var distanciaLongitude = grausParaRadianos((double) longitude2 - longitude1);
        var altitude = Math.sin((double) distanciaLatitude / DOIS) * Math.sin((double) distanciaLatitude / DOIS)
            + Math.cos(grausParaRadianos(latitude1))
            * Math.cos(grausParaRadianos(latitude2))
            * Math.sin(distanciaLongitude / DOIS)
            * Math.sin(distanciaLongitude / DOIS);
        var calculo = 2 * Math.atan2(Math.sqrt(altitude), Math.sqrt(UM - altitude));
        var distancia = RADIANOS_TERRA * calculo;
        return new BigDecimal(distancia * CONVERTER_METRO)
            .setScale(DOIS, RoundingMode.HALF_UP);
    }

    private static Double grausParaRadianos(Double deg) {
        return deg * (Math.PI / CENTO_OITENTA_GRAUS);
    }
}