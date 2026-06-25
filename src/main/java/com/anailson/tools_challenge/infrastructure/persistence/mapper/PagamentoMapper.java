package com.anailson.tools_challenge.infrastructure.persistence.mapper;

import com.anailson.tools_challenge.domain.model.DescricaoTransacao;
import com.anailson.tools_challenge.domain.model.FormaPagamento;
import com.anailson.tools_challenge.domain.model.Pagamento;
import com.anailson.tools_challenge.domain.model.Transacao;
import com.anailson.tools_challenge.infrastructure.persistence.entity.PagamentoEntity;
import org.springframework.stereotype.Component;

@Component
public class PagamentoMapper {

    public PagamentoEntity toEntity(Pagamento pagamento) {
        return PagamentoEntity.builder()
                .id(pagamento.getId())
                .idTransacao(pagamento.getTransacao().getId())
                .cartao(pagamento.getTransacao().getCartao())
                .valor(pagamento.getTransacao().getDescricao().getValor())
                .dataHora(pagamento.getTransacao().getDescricao().getDataHora())
                .estabelecimento(pagamento.getTransacao().getDescricao().getEstabelecimento())
                .nsu(pagamento.getTransacao().getNsu())
                .codigoAutorizacao(pagamento.getTransacao().getCodigoAutorizacao())
                .status(pagamento.getTransacao().getStatus())
                .tipoPagamento(pagamento.getFormaPagamento().getTipo())
                .parcelas(pagamento.getFormaPagamento().getParcelas())
                .build();
    }

    public Pagamento toDomain(PagamentoEntity entity) {

        return Pagamento.builder()
                .id((entity.getId()))
                .transacao(
                        Transacao.builder()
                                .id(entity.getIdTransacao())
                                .cartao(entity.getCartao())
                                .descricao(
                                        DescricaoTransacao.builder()
                                                .valor(entity.getValor())
                                                .dataHora(entity.getDataHora())
                                                .estabelecimento(entity.getEstabelecimento())
                                                .build()
                                )
                                .nsu(entity.getNsu())
                                .codigoAutorizacao(entity.getCodigoAutorizacao())
                                .status(entity.getStatus())
                                .build()
                )
                .formaPagamento(
                        FormaPagamento.builder()
                                .tipo(entity.getTipoPagamento())
                                .parcelas(entity.getParcelas())
                                .build()
                )
                .build();
    }


}
