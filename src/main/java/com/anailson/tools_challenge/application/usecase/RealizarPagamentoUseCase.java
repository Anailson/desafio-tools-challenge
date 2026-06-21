package com.anailson.tools_challenge.application.usecase;

import com.anailson.tools_challenge.adapters.repository.PagamentoRepository;
import com.anailson.tools_challenge.domain.enums.StatusTransacao;
import com.anailson.tools_challenge.domain.enums.TipoPagamento;
import com.anailson.tools_challenge.domain.model.Pagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RealizarPagamentoUseCase {

    private final PagamentoRepository pagamentoRepository;

    public Pagamento executar(Pagamento pagamento) {
        validarPagamento(pagamento);

        pagamento.getTransacao().setNsu(gerarNsu());
        pagamento.getTransacao().setCodigoAutorizacao(gerarCodigoAutorizacao());
        pagamento.getTransacao().setStatus(StatusTransacao.AUTORIZADO);

        return pagamentoRepository.salvar(pagamento);
    }

    private void validarPagamento(Pagamento pagamento) {
        if (pagamento == null || pagamento.getTransacao() == null) {
            throw new IllegalArgumentException("Dados do pagamento são obrigatórios");
        }

        if (pagamento.getTransacao().getId() == null || pagamento.getTransacao().getId().isBlank()) {
            throw new IllegalArgumentException("ID da transação é obrigatório");
        }

        if (pagamento.getTransacao().getDescricao() == null ||
                pagamento.getTransacao().getDescricao().getValor() == null ||
                pagamento.getTransacao().getDescricao().getValor().signum() <= 0) {
            throw new IllegalArgumentException("Valor da transação deve ser maior que zero");
        }

        if (pagamento.getFormaPagamento() == null || pagamento.getFormaPagamento().getTipo() == null) {
            throw new IllegalArgumentException("Forma de pagamento é obrigatória");
        }

        validarParcelas(pagamento);
    }

    private void validarParcelas(Pagamento pagamento) {
        TipoPagamento tipo = pagamento.getFormaPagamento().getTipo();
        Integer parcelas = pagamento.getFormaPagamento().getParcelas();

        if (parcelas == null || parcelas <= 0) {
            throw new IllegalArgumentException("Quantidade de parcelas é obrigatória");
        }

        if (tipo == TipoPagamento.AVISTA && parcelas != 1) {
            throw new IllegalArgumentException("Pagamento à vista deve ter apenas 1 parcela");
        }

        if ((tipo == TipoPagamento.PARCELADO_LOJA || tipo == TipoPagamento.PARCELADO_EMISSOR) && parcelas <= 1) {
            throw new IllegalArgumentException("Pagamento parcelado deve ter mais de 1 parcela");
        }
    }

    private String gerarNsu() {
        return UUID.randomUUID().toString().substring(0, 10);
    }

    private String gerarCodigoAutorizacao() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
