package br.com.cadeiralivreempresaapi.modulos.usuario.repository;

import br.com.cadeiralivreempresaapi.modulos.usuario.model.Usuario;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioRepositoryCustom {

    List<Usuario> findAllPredicate(Predicate build);

    List<Usuario> findAllPredicatePageable(Pageable pageable, Predicate predicate);
}
