package com.b2vnradarapi.b2vnradarapi.modules.radar.repository;

import com.b2vnradarapi.b2vnradarapi.modules.radar.model.BaseRadares;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BaseRadaresRepository extends JpaRepository<BaseRadares, Integer>,
    BaseRadaresRepositoryCustom {

    Page<BaseRadares> findByCodigoIn(Set<String> ids, Pageable pageable);

    Optional<BaseRadares> findByCodigoIgnoreCaseContaining(String codigo);

    Optional<BaseRadares> findByLatitudeLIgnoreCaseContaining(String latitudeL);

    List<BaseRadares> findByLote(Integer lote);

    Page<BaseRadares> findByLote(Integer lote, Pageable pageable);

    List<BaseRadares> findByLoteIn(List<Integer> lotes);

    List<BaseRadares> findByEnquadrame(String enquadramento);

    Page<BaseRadares> findByEnquadrame(String enquadramento, Pageable pageable);

    Page<BaseRadares> findByVelocidadeContainingIgnoreCaseOrderByVelocidadeAsc(String velocidade, Pageable pageable);
}
