package br.com.cadeiralivreempresaapi.modulos.agenda.repository;

import br.com.cadeiralivreempresaapi.modulos.agenda.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Integer> {

    Boolean existsByHorarioAndEmpresaIdAndDiaDaSemanaId(LocalTime horario, Integer empresaId, Integer diaDaSemaanId);

    List<Horario> findByEmpresaIdOrderByHorario(Integer empresaId);
}
