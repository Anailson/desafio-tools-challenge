package com.anailson.tools_challenge.application.usecase;

import com.anailson.tools_challenge.adapters.repository.PagamentoRepository;
import com.anailson.tools_challenge.domain.enums.StatusTransacao;
import com.anailson.tools_challenge.domain.model.Pagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstornarPagamentoUseCase {

    private final PagamentoRepository pagamentoRepository;

    public Pagamento executar(String id){

        Pagamento pagamento  = pagamentoRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada"));

        StatusTransacao statusTransacao = pagamento.getTransacao().getStatus();

        if (statusTransacao == StatusTransacao.CANCELADO){
            throw new IllegalArgumentException("Transação já foi estornada");
        }
        if(statusTransacao == StatusTransacao.NEGADO){
            throw new IllegalArgumentException("Transação negada não pode ser estornada");
        }

        pagamento.getTransacao().setStatus(StatusTransacao.CANCELADO);

        return pagamentoRepository.atualizar(pagamento);
    }

}
