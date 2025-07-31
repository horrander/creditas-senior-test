package br.com.creditas.simulador_emprestimo.service.impl;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.creditas.simulador_emprestimo.dto.SimulacaoRequestDTO;
import br.com.creditas.simulador_emprestimo.dto.SimulacaoResponseDTO;
import br.com.creditas.simulador_emprestimo.service.SimuladorService;

@Service
public class SimuladorServiceImpl implements SimuladorService {

    private static final Logger logger = Logger.getLogger(SimuladorServiceImpl.class.getName());

    @Override
    public SimulacaoResponseDTO simularEmprestimo(SimulacaoRequestDTO request) {

        logger.info("Iniciando simulação de empréstimo: " + request);

        int idadeCliente = obterIdadeCliente(request.getDataNascimento());
        double taxaJurosMensal = obterTaxaJurosMensal(idadeCliente);
        double valorPresenteComTaxaAplicada = request.getValorSolicitado() * taxaJurosMensal;
        double divisor = 1 - Math.pow((1 + taxaJurosMensal), -request.getNumeroParcelas());
        double pmt = valorPresenteComTaxaAplicada / divisor;
        double valorTotalPago = pmt * request.getNumeroParcelas();
        double valorTotalJuros = valorTotalPago - request.getValorSolicitado();

        return new SimulacaoResponseDTO(pmt, valorTotalPago, valorTotalJuros);
    }

    private double obterTaxaJurosMensal(int idadeCliente) {

        logger.info("Obtendo taxa de juros mensal para idade do cliente: " + idadeCliente);

        if (idadeCliente <= 0) {
            throw new IllegalArgumentException("Idade do cliente inválida");
        }

        double taxaJurosAnual;

        if (idadeCliente <= 25) {
            taxaJurosAnual = 5L;
        } else if (idadeCliente <= 40) {
            taxaJurosAnual = 3L;
        } else if (idadeCliente <= 60) {
            taxaJurosAnual = 2L;
        } else {
            taxaJurosAnual = 4L;
        }

        double taxaJurosMensal = taxaJurosAnual / 12;

        logger.info("Taxa de juros mensal obtida: " + taxaJurosMensal);

        return taxaJurosMensal;
    }

    private int obterIdadeCliente(LocalDateTime dataNascimento) {

        logger.info("Calculando idade do cliente com base na data de nascimento: " + dataNascimento);

        if (dataNascimento == null) {

            throw new IllegalArgumentException("Data de nascimento não pode ser nula");
        }

        int idade = Period.between(dataNascimento.toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();

        logger.info("Idade do cliente calculada: " + idade);

        return idade;
    }
}
