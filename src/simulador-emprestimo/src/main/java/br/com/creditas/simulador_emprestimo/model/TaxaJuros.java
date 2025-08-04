package br.com.creditas.simulador_emprestimo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaxaJuros {

    private int id;
    private int idadeInicial;
    private int idadeFinal;
    private double taxaAnual;

    /**
     * Calcula a taxa de juros mensal com base na taxa anual.
     *
     * @return Taxa de juros mensal.
     */
    public double calcularTaxaJurosMensal() {

        return taxaAnual / 12;
    }
}
