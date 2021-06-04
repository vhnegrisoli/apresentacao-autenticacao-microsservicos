package br.com.b2vnauthapi.b2vnauthapi.modules.log.repository;

import br.com.b2vnauthapi.b2vnauthapi.modules.log.model.Log;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static br.com.b2vnauthapi.b2vnauthapi.modules.log.model.QLog.log;

@Repository
@SuppressWarnings("PMD.TooManyStaticImports")
public class LogRepositoryImpl implements LogRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Log> findAllPageable(PageRequest pageable, Predicate build) {
        return new JPAQuery<Void>(entityManager)
            .select(log)
            .from(log)
            .fetch();
    }
}
