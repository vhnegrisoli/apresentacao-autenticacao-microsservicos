package br.com.cadeiralivreempresaapi.modulos.agenda.dto.horario;

import br.com.cadeiralivreempresaapi.modulos.agenda.model.DiaDaSemana;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiaDaSemanaResponse {

    private Integer id;
    private Integer dia;
    private String diaNome;

    public static DiaDaSemanaResponse of(DiaDaSemana diaDaSemana) {
        var response = new DiaDaSemanaResponse();
        BeanUtils.copyProperties(diaDaSemana, response);
        return response;
    }
}
