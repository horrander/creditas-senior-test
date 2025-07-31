package br.com.creditas.simulador_emprestimo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SimulacaoRequestDTO {

    private double valorSolicitado;
    private LocalDateTime dataNascimento;
    private Integer numeroParcelas;
}
