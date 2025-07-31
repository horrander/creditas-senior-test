package br.com.creditas.simulador_emprestimo.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SimulacaoResponseDTO {

    private double valorTotalPagamento;
    private double valorParcela;
    private double valorTotalJuros;

    public SimulacaoResponseDTO(double valorParcela,
                                double valorTotalPagamento,
                                double valorTotalJuros){

        this.valorParcela = valorParcela;
    }
}
