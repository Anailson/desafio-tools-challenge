package com.anailson.tools_challenge.application.usecase;

import com.anailson.tools_challenge.adapters.repository.PagamentoRepository;
import com.anailson.tools_challenge.domain.enums.StatusTransacao;
import com.anailson.tools_challenge.domain.model.Pagamento;
import com.anailson.tools_challenge.domain.model.Transacao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstornarPagamentoUseCaseTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private EstornarPagamentoUseCase estornarPagamentoUseCase;

    @Test
    void deveEstornarPagamentoComSucesso() {

        Pagamento pagamento = Pagamento.builder()
                .transacao(
                        Transacao.builder()
                                .id("100023568900001")
                                .status(StatusTransacao.AUTORIZADO)
                                .build())
                .build();

        when(pagamentoRepository.buscarPorId("100023568900001"))
                .thenReturn(Optional.of(pagamento));

        when(pagamentoRepository.atualizar(any(Pagamento.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Pagamento resultado =
                estornarPagamentoUseCase.executar("100023568900001");

        assertEquals(
                StatusTransacao.CANCELADO,
                resultado.getTransacao().getStatus());

        verify(pagamentoRepository).buscarPorId("100023568900001");
        verify(pagamentoRepository).atualizar(any(Pagamento.class));
    }

    @Test
    void deveLancarExcecaoQuandoTransacaoNaoEncontrada() {

        when(pagamentoRepository.buscarPorId("100023568900001"))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> estornarPagamentoUseCase.executar("100023568900001"));

        assertEquals("Transação não encontrada", exception.getMessage());

        verify(pagamentoRepository).buscarPorId("100023568900001");
        verify(pagamentoRepository, never()).atualizar(any());
    }

    @Test
    void deveLancarExcecaoQuandoTransacaoJaEstornada() {

        Pagamento pagamento = Pagamento.builder()
                .transacao(
                        Transacao.builder()
                                .status(StatusTransacao.CANCELADO)
                                .build())
                .build();

        when(pagamentoRepository.buscarPorId("100023568900001"))
                .thenReturn(Optional.of(pagamento));

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> estornarPagamentoUseCase.executar("100023568900001"));

        assertEquals("Transação já foi estornada", exception.getMessage());

        verify(pagamentoRepository).buscarPorId("100023568900001");
        verify(pagamentoRepository, never()).atualizar(any());
    }

    @Test
    void deveLancarExcecaoQuandoTransacaoNegada() {

        Pagamento pagamento = Pagamento.builder()
                .transacao(
                        Transacao.builder()
                                .status(StatusTransacao.NEGADO)
                                .build())
                .build();

        when(pagamentoRepository.buscarPorId("100023568900001"))
                .thenReturn(Optional.of(pagamento));

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> estornarPagamentoUseCase.executar("100023568900001"));

        assertEquals("Transação negada não pode ser estornada", exception.getMessage());

        verify(pagamentoRepository).buscarPorId("100023568900001");
        verify(pagamentoRepository, never()).atualizar(any());
    }

}