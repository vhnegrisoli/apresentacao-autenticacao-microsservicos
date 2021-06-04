package br.com.cadeiralivreempresaapi.modulos.agenda.dto.horario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HorarioRequest {

    private Integer empresaId;
    private LocalTime horario;
    private Integer diaSemanaId;
}
