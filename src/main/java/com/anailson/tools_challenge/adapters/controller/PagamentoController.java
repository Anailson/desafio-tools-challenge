package com.anailson.tools_challenge.adapters.controller;


import com.anailson.tools_challenge.application.usecase.BuscarPagamentoUseCase;
import com.anailson.tools_challenge.application.usecase.EstornarPagamentoUseCase;
import com.anailson.tools_challenge.application.usecase.RealizarPagamentoUseCase;
import com.anailson.tools_challenge.domain.model.Pagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final RealizarPagamentoUseCase realizarPagamentoUseCase;
    private final BuscarPagamentoUseCase buscarPagamentoUseCase;
    private final EstornarPagamentoUseCase estornarPagamentoUseCase;


    @PostMapping
    public ResponseEntity<Pagamento> realizarPagamento(@RequestBody Pagamento pagamento) {
        Pagamento pagamentoRealizado = realizarPagamentoUseCase.executar(pagamento);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pagamentoRealizado);
    }

    @GetMapping
    public List<Pagamento> buscarTodos() {
        return ResponseEntity.ok(buscarPagamentoUseCase.buscarTodos()).getBody();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> buscarPOrId(@PathVariable String id) {
        return ResponseEntity.ok(buscarPagamentoUseCase.buscarPOrId(id));
    }

    @PutMapping("/{id}/estorno")
    public ResponseEntity<Pagamento> estornar(@PathVariable String id){
        return ResponseEntity.ok(estornarPagamentoUseCase.executar(id));
    }

}
