package br.com.creditas.simulador_emprestimo.dto;

import br.com.creditas.simulador_emprestimo.model.SimulacaoEmprestimo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SimulacaoRequestDto {

    @Schema(description = "Valor solicitado para o empréstimo", example = "10000")
    private double valorSolicitado;

    @Schema(description = "Número de parcelas para o pagamento", example = "12")
    private int numeroParcelas;

    @Schema(description = "Código alfabético da moeda", example = "BRL")
    private String codigoAlfabeticoMoeda;

    @Schema(description = "CPF do cliente", example = "12345678901")
    private String cpfCliente;

    public SimulacaoEmprestimo toModel() {

        return new SimulacaoEmprestimo(getValorSolicitado(),
                getNumeroParcelas(),
                getCodigoAlfabeticoMoeda(),
                getCpfCliente());
    }
}
