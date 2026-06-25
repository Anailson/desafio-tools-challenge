package com.anailson.tools_challenge.application.usecase;

import com.anailson.tools_challenge.adapters.repository.PagamentoRepository;
import com.anailson.tools_challenge.domain.enums.StatusTransacao;
import com.anailson.tools_challenge.domain.enums.TipoPagamento;
import com.anailson.tools_challenge.domain.model.DescricaoTransacao;
import com.anailson.tools_challenge.domain.model.FormaPagamento;
import com.anailson.tools_challenge.domain.model.Pagamento;
import com.anailson.tools_challenge.domain.model.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RealizarPagamentoUseCaseTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private RealizarPagamentoUseCase useCase;

    private Pagamento pagamento;

    @BeforeEach
    void setUp() {
        pagamento = criarPagamentoValido();
    }

    @Test
    void deveRealizarPagamentoComSucesso() {

        when(pagamentoRepository.salvar(any(Pagamento.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Pagamento resultado = useCase.executar(pagamento);

        assertNotNull(resultado);
        assertEquals(StatusTransacao.AUTORIZADO,
                resultado.getTransacao().getStatus());

        assertNotNull(resultado.getTransacao().getNsu());
        assertFalse(resultado.getTransacao().getNsu().isBlank());

        assertNotNull(resultado.getTransacao().getCodigoAutorizacao());
        assertFalse(resultado.getTransacao().getCodigoAutorizacao().isBlank());

        verify(pagamentoRepository, times(1))
                .salvar(any(Pagamento.class));
    }

    @Test
    void deveLancarExcecaoQuandoPagamentoForNulo() {

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> useCase.executar(null));

        assertEquals("Dados do pagamento são obrigatórios",
                exception.getMessage());

        verifyNoInteractions(pagamentoRepository);
    }

    @Test
    void deveLancarExcecaoQuandoValorForMenorOuIgualAZero() {

        pagamento.getTransacao()
                .getDescricao()
                .setValor(BigDecimal.ZERO);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> useCase.executar(pagamento));

        assertEquals("Valor da transação deve ser maior que zero",
                exception.getMessage());

        verifyNoInteractions(pagamentoRepository);
    }

    @Test
    void deveLancarExcecaoQuandoPagamentoAvistaPossuirMaisDeUmaParcela() {

        pagamento.getFormaPagamento().setTipo(TipoPagamento.AVISTA);
        pagamento.getFormaPagamento().setParcelas(2);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> useCase.executar(pagamento));

        assertEquals("Pagamento à vista deve ter apenas 1 parcela",
                exception.getMessage());

        verifyNoInteractions(pagamentoRepository);
    }

    @Test
    void deveLancarExcecaoQuandoPagamentoParceladoPossuirUmaParcela() {

        pagamento.getFormaPagamento()
                .setTipo(TipoPagamento.PARCELADO_LOJA);

        pagamento.getFormaPagamento()
                .setParcelas(1);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> useCase.executar(pagamento));

        assertEquals("Pagamento parcelado deve ter mais de 1 parcela",
                exception.getMessage());

        verifyNoInteractions(pagamentoRepository);
    }

    private Pagamento criarPagamentoValido() {

        return Pagamento.builder()
                .transacao(
                        Transacao.builder()
                                .id("100023568900001")
                                .cartao("4444333322221111")
                                .descricao(
                                        DescricaoTransacao.builder()
                                                .valor(BigDecimal.valueOf(500.50))
                                                .dataHora(LocalDateTime.now())
                                                .estabelecimento("PetShop Mundo Cão")
                                                .build()
                                )
                                .build()
                )
                .formaPagamento(
                        FormaPagamento.builder()
                                .tipo(TipoPagamento.AVISTA)
                                .parcelas(1)
                                .build()
                )
                .build();
    }
}