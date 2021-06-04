package com.b2vnradarapi.b2vnradarapi.modules.radar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RadaresVelocidadeResponse {

    private String velocidade;
    private Long totalRadares;

}
