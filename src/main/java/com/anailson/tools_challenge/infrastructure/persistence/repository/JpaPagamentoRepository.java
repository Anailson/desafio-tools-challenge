package com.anailson.tools_challenge.infrastructure.persistence.repository;

import com.anailson.tools_challenge.infrastructure.persistence.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPagamentoRepository extends JpaRepository<PagamentoEntity, Long> {

}
