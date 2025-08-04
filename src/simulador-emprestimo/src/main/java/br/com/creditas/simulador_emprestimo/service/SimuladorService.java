package br.com.creditas.simulador_emprestimo.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import br.com.creditas.simulador_emprestimo.dto.SimulacaoRequestDto;
import br.com.creditas.simulador_emprestimo.dto.SimulacaoResponseDto;

public interface SimuladorService {

   /**
    * Simula um empréstimo com base nos dados fornecidos na requisição.
    *
    * @param request Dados da simulação de empréstimo.
    * @return Resposta da simulação contendo os detalhes do empréstimo.
    */
   public SimulacaoResponseDto simularEmprestimo(SimulacaoRequestDto request);

   /**
    * Simula um lote de empréstimos com base em uma lista de requisições.
    *
    * @param simulacoes Lista de requisições de simulação de empréstimos.
    * @return Lista de respostas das simulações contendo os detalhes dos
    *         empréstimos.
    */
   public CompletableFuture<List<SimulacaoResponseDto>> simularEmprestimoLote(List<SimulacaoRequestDto> simulacoes);
}
