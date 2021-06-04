package com.b2vnradarapi.b2vnradarapi.modules.trajetos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrajetosPageResponse {

    private Long totalElements;
    private Page<TrajetosResponse> trajetosResponse;

}
