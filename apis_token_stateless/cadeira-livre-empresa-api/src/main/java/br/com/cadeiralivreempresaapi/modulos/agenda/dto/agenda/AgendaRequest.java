package br.com.cadeiralivreempresaapi.modulos.agenda.dto.agenda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendaRequest {

    private Integer horarioId;
    private List<Integer> servicosIds;
    private Integer empresaId;
}
