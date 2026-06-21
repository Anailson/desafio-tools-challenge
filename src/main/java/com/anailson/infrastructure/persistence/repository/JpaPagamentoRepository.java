package com.anailson.infrastructure.persistence.repository;

import com.anailson.infrastructure.persistence.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPagamentoRepository extends JpaRepository<PagamentoEntity, Long> {

}
