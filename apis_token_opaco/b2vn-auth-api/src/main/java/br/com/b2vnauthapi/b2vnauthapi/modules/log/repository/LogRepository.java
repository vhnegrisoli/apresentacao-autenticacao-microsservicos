package br.com.b2vnauthapi.b2vnauthapi.modules.log.repository;

import br.com.b2vnauthapi.b2vnauthapi.modules.log.model.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Integer>, LogRepositoryCustom {

    Page<Log> findByMetodo(String metodo, Pageable pageable);
}
