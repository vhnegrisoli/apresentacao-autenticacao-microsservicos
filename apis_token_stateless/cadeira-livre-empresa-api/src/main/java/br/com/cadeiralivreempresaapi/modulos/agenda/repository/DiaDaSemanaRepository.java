package br.com.cadeiralivreempresaapi.modulos.agenda.repository;

import br.com.cadeiralivreempresaapi.modulos.agenda.enums.EDiaDaSemana;
import br.com.cadeiralivreempresaapi.modulos.agenda.model.DiaDaSemana;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaDaSemanaRepository extends JpaRepository<DiaDaSemana, Integer> {

    Optional<DiaDaSemana> findByDia(Integer dia);

    Optional<DiaDaSemana> findByDiaCodigo(EDiaDaSemana diaCodigo);
}
