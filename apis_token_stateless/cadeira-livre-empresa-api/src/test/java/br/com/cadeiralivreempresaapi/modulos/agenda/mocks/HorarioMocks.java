package br.com.cadeiralivreempresaapi.modulos.agenda.mocks;

import br.com.cadeiralivreempresaapi.modulos.agenda.dto.horario.DiaDaSemanaResponse;
import br.com.cadeiralivreempresaapi.modulos.agenda.dto.horario.HorarioRequest;
import br.com.cadeiralivreempresaapi.modulos.agenda.dto.horario.HorarioResponse;
import br.com.cadeiralivreempresaapi.modulos.agenda.enums.EDiaDaSemana;
import br.com.cadeiralivreempresaapi.modulos.agenda.model.DiaDaSemana;
import br.com.cadeiralivreempresaapi.modulos.agenda.model.Horario;

import java.time.LocalTime;

import static br.com.cadeiralivreempresaapi.modulos.empresa.mocks.EmpresaMocks.umaEmpresa;

public class HorarioMocks {

    public static Horario umHorario() {
        return Horario
            .builder()
            .id(1)
            .horario(LocalTime.of(12, 0))
            .diaDaSemana(umDiaDaSemana())
            .empresa(umaEmpresa())
            .build();
    }

    public static DiaDaSemana umDiaDaSemana() {
        return DiaDaSemana
            .builder()
            .id(1)
            .dia(0)
            .diaCodigo(EDiaDaSemana.SEGUNDA_FEIRA)
            .diaNome(EDiaDaSemana.SEGUNDA_FEIRA.getDiaDaSemana())
            .build();
    }

    public static DiaDaSemanaResponse umDiaDaSemanaResponse() {
        return DiaDaSemanaResponse
            .builder()
            .id(1)
            .dia(0)
            .diaNome(EDiaDaSemana.SEGUNDA_FEIRA.getDiaDaSemana())
            .build();
    }

    public static HorarioRequest umHorarioRequest() {
        return HorarioRequest
            .builder()
            .diaSemanaId(1)
            .horario(LocalTime.of(12, 0))
            .empresaId(1)
            .build();
    }

    public static HorarioResponse umHorarioResponse() {
        return HorarioResponse.of(umHorario());
    }
}
