package com.anailson.tools_challenge.adapters.controller;


import com.anailson.tools_challenge.application.usecase.BuscarPagamentoUseCase;
import com.anailson.tools_challenge.application.usecase.EstornarPagamentoUseCase;
import com.anailson.tools_challenge.application.usecase.RealizarPagamentoUseCase;
import com.anailson.tools_challenge.domain.model.Pagamento;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Pagamentos", description = "Operações de pagamento")
@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final RealizarPagamentoUseCase realizarPagamentoUseCase;
    private final BuscarPagamentoUseCase buscarPagamentoUseCase;
    private final EstornarPagamentoUseCase estornarPagamentoUseCase;


    @Operation(summary = "Realizar pagamento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pagamento realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<Pagamento> realizarPagamento(@RequestBody Pagamento pagamento) {
        Pagamento pagamentoRealizado = realizarPagamentoUseCase.executar(pagamento);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pagamentoRealizado);
    }

    @Operation(summary = "Listar pagamentos")
    @GetMapping
    public List<Pagamento> buscarTodos() {
        return ResponseEntity.ok(buscarPagamentoUseCase.buscarTodos()).getBody();

    }

    @Operation(summary = "Buscar pagamento por ID da transação")
    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> buscarPOrId(@PathVariable String id) {
        return ResponseEntity.ok(buscarPagamentoUseCase.buscarPOrId(id));
    }

    @Operation(summary = "Estornar pagamento")
    @PutMapping("/{id}/estorno")
    public ResponseEntity<Pagamento> estornar(@PathVariable String id){
        return ResponseEntity.ok(estornarPagamentoUseCase.executar(id));
    }

}
