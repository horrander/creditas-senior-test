package br.com.creditas.simulador_emprestimo.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.creditas.simulador_emprestimo.dto.ErrorResponseDto;
import br.com.creditas.simulador_emprestimo.dto.SimulacaoRequestDto;
import br.com.creditas.simulador_emprestimo.dto.SimulacaoResponseDto;
import br.com.creditas.simulador_emprestimo.service.SimuladorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SimuladorController {

    private final SimuladorService simuladorService;

    @PostMapping()
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulação realizada com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = SimulacaoRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, erro de négocio ou validação", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro técnino inesperado", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDto.class)))
    })
    @Operation(summary = "Simula um empréstimo com base nos dados fornecidos", description = "Realiza a simulação de um empréstimo com base nos dados do cliente e retorna os valores calculados.")
    public ResponseEntity<SimulacaoResponseDto> simularEmprestimo(@RequestBody SimulacaoRequestDto request) {

        return ResponseEntity.ok(simuladorService.simularEmprestimo(request));
    }

    @PostMapping("/lote")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulação realizada com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = SimulacaoRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, erro de négocio ou validação", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro técnino inesperado", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDto.class)))
    })
    @Operation(summary = "Simula um empréstimo com base nos dados fornecidos", description = "Realiza a simulação de um empréstimo com base nos dados do cliente e retorna os valores calculados.")
    public CompletableFuture<List<SimulacaoResponseDto>> simularEmprestimoLote(
            @RequestBody List<SimulacaoRequestDto> simulacoes) {

        return simuladorService.simularEmprestimoLote(simulacoes);
    }
}
