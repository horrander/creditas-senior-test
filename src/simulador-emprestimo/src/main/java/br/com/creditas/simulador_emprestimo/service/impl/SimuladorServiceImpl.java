package br.com.creditas.simulador_emprestimo.service.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.creditas.simulador_emprestimo.dto.SimulacaoRequestDto;
import br.com.creditas.simulador_emprestimo.dto.SimulacaoResponseDto;
import br.com.creditas.simulador_emprestimo.service.ClienteService;
import br.com.creditas.simulador_emprestimo.service.SimuladorService;
import br.com.creditas.simulador_emprestimo.service.TaxaJurosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimuladorServiceImpl implements SimuladorService {

    private final ClienteService clienteService;
    private final TaxaJurosService taxaJurosService;

    @Override
    public SimulacaoResponseDto simularEmprestimo(SimulacaoRequestDto request) {

        log.info("Iniciando simulação de empréstimo: {}", request);

        var simulacaoEmprestimo = request.toModel();
        int idadeCliente = obterIdadeCliente(simulacaoEmprestimo.getCpfCliente());
        double taxaJurosMensal = taxaJurosService.buscarTaxaJurosMensalPorIdade(idadeCliente);
        simulacaoEmprestimo.calcularValoresSimulacao(taxaJurosMensal);

        log.info("Simulação de empréstimo concluída: {}", simulacaoEmprestimo);

        return new SimulacaoResponseDto().fromModel(simulacaoEmprestimo);
    }

    @Async
    @Override
    public CompletableFuture<List<SimulacaoResponseDto>> simularEmprestimoLote(List<SimulacaoRequestDto> simulacoes) {

        log.info("Iniciando simulação de lote de empréstimos:");

        return CompletableFuture.supplyAsync(() ->

        simulacoes.stream()
                .map(this::simularEmprestimo)
                .toList());
    }

    /**
     * Calcula a idade do cliente com base na data de nascimento.
     *
     * @param dataNascimento Data de nascimento do cliente.
     * @return Idade do cliente em anos.
     */
    private int obterIdadeCliente(String cpfCliente) {

        var cliente = clienteService.buscarClientePorCpf(cpfCliente);

        log.info("Cliente encontrado: {}", cliente);

        int idade = cliente.calcularIdadeCliente();

        log.info("Idade do cliente calculada: {}", idade);

        return idade;
    }
}
