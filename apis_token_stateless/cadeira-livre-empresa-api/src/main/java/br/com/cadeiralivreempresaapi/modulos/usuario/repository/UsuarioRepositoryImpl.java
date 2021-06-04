package br.com.cadeiralivreempresaapi.modulos.usuario.repository;

import br.com.cadeiralivreempresaapi.modulos.usuario.model.Usuario;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static br.com.cadeiralivreempresaapi.modulos.usuario.model.QUsuario.usuario;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepositoryCustom {

    @Autowired
    private EntityManager entityManager;
    protected Querydsl querydsl;

    @Override
    public List<Usuario> findAllPredicate(Predicate predicate) {
        return new JPAQuery<Void>(entityManager)
            .select(usuario)
            .from(usuario)
            .where(predicate)
            .fetch();
    }

    @Override
    public List<Usuario> findAllPredicatePageable(Pageable pageable, Predicate predicate) {
        var path = SimpleEntityPathResolver.INSTANCE.createPath(Usuario.class);
        var querydsl = new Querydsl(entityManager, new PathBuilder<Usuario>(path.getType(), path.getMetadata()));
        return querydsl.applyPagination(
            pageable,
            new JPAQuery<Void>(entityManager)
                .select(usuario)
                .from(usuario)
                .where(predicate)
        ).fetch();
    }
}