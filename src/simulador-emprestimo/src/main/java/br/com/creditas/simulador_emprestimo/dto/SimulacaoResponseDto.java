package br.com.creditas.simulador_emprestimo.dto;

import br.com.creditas.simulador_emprestimo.model.SimulacaoEmprestimo;
import br.com.creditas.simulador_emprestimo.util.MoedaUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SimulacaoResponseDto {

    @Schema(description = "Valor da parcela do empréstimo", example = "R$ 10.000,00")
    private String valorParcela;

    @Schema(description = "Valor total a ser pago pelo empréstimo", example = "R$ 30.000.00")
    private String valorTotalPagamento;

    @Schema(description = "Valor total de juros do empréstimo", example = "R$ 20.000,00")
    private String valorTotalJuros;

    public SimulacaoResponseDto fromModel(SimulacaoEmprestimo model) {

        this.valorParcela = MoedaUtil.formatarMoeda(model.getCodigoAlfabeticoMoeda(), model.getValorParcela());
        this.valorTotalPagamento = MoedaUtil.formatarMoeda(model.getCodigoAlfabeticoMoeda(),
                model.getValorTotalPagamento());
        this.valorTotalJuros = MoedaUtil.formatarMoeda(model.getCodigoAlfabeticoMoeda(), model.getValorTotalJuros());

        return this;
    }
}
