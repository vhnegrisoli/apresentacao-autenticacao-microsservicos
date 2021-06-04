package br.com.cadeiralivreempresaapi.modulos.funcionario.repository;

import br.com.cadeiralivreempresaapi.modulos.funcionario.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer>,
    QuerydslPredicateExecutor<Funcionario> {

    Optional<Funcionario> findByUsuarioId(Integer usuarioId);

    Boolean existsByUsuarioIdAndEmpresaId(Integer usuarioId, Integer empresaId);
}