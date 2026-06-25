package com.anailson.tools_challenge.application.usecase;

import com.anailson.tools_challenge.adapters.repository.PagamentoRepository;
import com.anailson.tools_challenge.domain.model.Pagamento;
import com.anailson.tools_challenge.domain.model.Transacao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarPagamentoUseCaseTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private BuscarPagamentoUseCase buscarPagamentoUseCase;

    @Test
    void deveBuscarPagamentoPorId() {

        Pagamento pagamento = Pagamento.builder()
                .transacao(
                        Transacao.builder()
                                .id("100023568900001")
                                .build())
                .build();

        when(pagamentoRepository.buscarPorId("100023568900001"))
                .thenReturn(Optional.of(pagamento));

        Pagamento resultado = buscarPagamentoUseCase.buscarPOrId("100023568900001");

        assertNotNull(resultado);
        assertEquals("100023568900001", resultado.getTransacao().getId());

        verify(pagamentoRepository).buscarPorId("100023568900001");
    }

    @Test
    void deveLancarExcecaoQuandoPagamentoNaoEncontrado() {

        when(pagamentoRepository.buscarPorId("100023568900001"))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> buscarPagamentoUseCase.buscarPOrId("100023568900001"));

        assertEquals("Transação não encontrada", exception.getMessage());

        verify(pagamentoRepository).buscarPorId("100023568900001");
    }

    @Test
    void deveBuscarTodosOsPagamentos() {

        List<Pagamento> pagamentos = List.of(
                Pagamento.builder().build(),
                Pagamento.builder().build()
        );

        when(pagamentoRepository.buscarTodos())
                .thenReturn(pagamentos);

        List<Pagamento> resultado = buscarPagamentoUseCase.buscarTodos();

        assertEquals(2, resultado.size());

        verify(pagamentoRepository).buscarTodos();
    }
}