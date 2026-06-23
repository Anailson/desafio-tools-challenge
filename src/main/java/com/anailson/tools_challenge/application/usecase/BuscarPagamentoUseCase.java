package com.anailson.tools_challenge.application.usecase;

import com.anailson.tools_challenge.adapters.repository.PagamentoRepository;
import com.anailson.tools_challenge.domain.model.Pagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuscarPagamentoUseCase {

    private final PagamentoRepository pagamentoRepository;

    public Pagamento buscarPOrId(String id){
        return pagamentoRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada"));
    }

    public List<Pagamento> buscarTodos(){
        return pagamentoRepository.buscarTodos();

    }
}
