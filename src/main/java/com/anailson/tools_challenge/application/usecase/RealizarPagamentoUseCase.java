package com.anailson.tools_challenge.application.usecase;

import com.anailson.tools_challenge.adapters.repository.PagamentoRepository;
import com.anailson.tools_challenge.domain.model.Pagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RealizarPagamentoUseCase {

    private final PagamentoRepository pagamentoRepository;

    public Pagamento Executar (Pagamento pagamento){
    return null;
    }
}
