package com.anailson.adapters.repository;

import com.anailson.domain.model.Pagamento;

import java.util.List;
import java.util.Optional;

public interface PagamentoRepository {

    Pagamento salvar(Pagamento pagamento);

    Optional<Pagamento> buscarPorId(Long id);

    List<Pagamento> buscarTodos();


}
