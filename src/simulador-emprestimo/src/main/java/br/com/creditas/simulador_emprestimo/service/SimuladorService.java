package br.com.creditas.simulador_emprestimo.service;

import br.com.creditas.simulador_emprestimo.dto.SimulacaoRequestDTO;
import br.com.creditas.simulador_emprestimo.dto.SimulacaoResponseDTO;

public interface SimuladorService {

   public SimulacaoResponseDTO simularEmprestimo(SimulacaoRequestDTO request);
}
