package com.anailson.tools_challenge.adapters.repository;

import com.anailson.tools_challenge.domain.model.Pagamento;

import java.util.List;
import java.util.Optional;

public interface PagamentoRepository {

    Pagamento salvar(Pagamento pagamento);

    Optional<Pagamento> buscarPorId(Long id);

    List<Pagamento> buscarTodos();


}
