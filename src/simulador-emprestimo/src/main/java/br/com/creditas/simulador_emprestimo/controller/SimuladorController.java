package br.com.creditas.simulador_emprestimo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.creditas.simulador_emprestimo.dto.SimulacaoRequestDTO;
import br.com.creditas.simulador_emprestimo.dto.SimulacaoResponseDTO;
import br.com.creditas.simulador_emprestimo.service.SimuladorService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SimuladorController {

    @Autowired
    private SimuladorService simuladorService;

    @PostMapping()
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulação realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public SimulacaoResponseDTO simularEmprestimo(@RequestBody SimulacaoRequestDTO request) {

        return simuladorService.simularEmprestimo(request);
    }
}
