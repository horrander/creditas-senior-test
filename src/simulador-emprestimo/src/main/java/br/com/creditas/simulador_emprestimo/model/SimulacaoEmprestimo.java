package br.com.creditas.simulador_emprestimo.model;

import br.com.creditas.simulador_emprestimo.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SimulacaoEmprestimo {

    private double valorSolicitado;
    private int numeroParcelas;
    private double valorParcela;
    private double valorTotalPagamento;
    private double taxaJurosMensal;
    private double valorTotalJuros;
    private String codigoAlfabeticoMoeda;
    private String cpfCliente;

    public SimulacaoEmprestimo(double valorSolicitado,
            int numeroParcelas,
            String codigoAlfabeticoMoeda,
            String cpfCliente) {

        if (valorSolicitado <= 0) {
            throw new BusinessException("Valor solicitado deve ser maior que 0");
        } else if (numeroParcelas < 1) {
            throw new BusinessException("Quantidade de parcelas precisa ser maior que 0");
        } else if (cpfCliente == null || cpfCliente.isEmpty()) {
            throw new BusinessException("CPF do cliente não pode ser nulo ou vazio");
        }

        this.valorSolicitado = valorSolicitado;
        this.numeroParcelas = numeroParcelas;
        this.codigoAlfabeticoMoeda = codigoAlfabeticoMoeda;
        this.cpfCliente = cpfCliente;
        this.valorParcela = 0.0;
        this.valorTotalPagamento = 0.0;
        this.taxaJurosMensal = 0.0;
        this.valorTotalJuros = 0.0;
    }

    /**
     * Calcula os valores da simulação de empréstimo
     * 
     * @param idadeCliente Idade do cliente em anos.
     */
    public void calcularValoresSimulacao(double taxaJurosMensal) {

        calcularPMT(taxaJurosMensal);
    }

    /**
     * Calcula o valor da parcela a ser pago de acordo com a fórmula abaixo.
     *
     * PMT = PV * i / (1 - (1 + i)^-n)
     * 
     */
    private void calcularPMT(double taxaJurosMensal) {

        double valorPresenteComTaxaAplicada = valorSolicitado * taxaJurosMensal;
        double divisor = 1 - Math.pow((1 + taxaJurosMensal), -numeroParcelas);
        double pmt = valorPresenteComTaxaAplicada / divisor;
        setValorTotalPagamento(pmt * numeroParcelas);
        setValorTotalJuros(valorTotalPagamento - valorSolicitado);
        setValorParcela(pmt);
    }
}
