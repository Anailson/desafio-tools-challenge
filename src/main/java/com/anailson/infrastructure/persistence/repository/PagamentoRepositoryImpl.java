package com.anailson.infrastructure.persistence.repository;


import com.anailson.adapters.repository.PagamentoRepository;
import com.anailson.domain.model.Pagamento;
import com.anailson.infrastructure.persistence.entity.PagamentoEntity;
import com.anailson.infrastructure.persistence.mapper.PagamentoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PagamentoRepositoryImpl implements PagamentoRepository {

    private final JpaPagamentoRepository jpaRepository;
    private final PagamentoMapper pagamentoMapper;

    @Override
    public Pagamento salvar(Pagamento pagamento) {

        PagamentoEntity entity = pagamentoMapper.toEntity(pagamento);

        PagamentoEntity saved = jpaRepository.save(entity);

        return pagamentoMapper.toDomain(saved);

    }

    @Override
    public Optional<Pagamento> buscarPorId(Long id) {

        return jpaRepository.findById(id)
                .map(pagamentoMapper::toDomain);
    }


    @Override
    public List<Pagamento> buscarTodos() {
        return jpaRepository.findAll()
                .stream()
                .map(pagamentoMapper::toDomain)
                .toList();
    }
}
