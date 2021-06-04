package br.com.b2vnauthapi.b2vnauthapi.modules.log.repository;

import br.com.b2vnauthapi.b2vnauthapi.modules.log.model.Log;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface LogRepositoryCustom {

    List<Log> findAllPageable(PageRequest pageable, Predicate build);
}
