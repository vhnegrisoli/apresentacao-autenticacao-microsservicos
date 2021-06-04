package br.com.cadeiralivreempresaapi.modulos.empresa.repository;

import br.com.cadeiralivreempresaapi.modulos.empresa.enums.ESituacaoEmpresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.model.Empresa;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer>,
    QuerydslPredicateExecutor<Empresa> {

    Boolean existsByIdAndSociosId(Integer id, Integer usuarioId);

    List<Empresa> findAll(Predicate predicate);

    Optional<Empresa> findByIdAndSituacao(Integer id, ESituacaoEmpresa situacao);
}

