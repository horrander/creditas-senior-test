package br.com.creditas.simulador_emprestimo.service.impl;

import org.springframework.stereotype.Service;

import br.com.creditas.simulador_emprestimo.exception.BusinessException;
import br.com.creditas.simulador_emprestimo.repository.TaxaJurosRepository;
import br.com.creditas.simulador_emprestimo.service.TaxaJurosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaxaJurosServiceImpl implements TaxaJurosService {

    private final TaxaJurosRepository taxaJurosRepository;

    @Override
    public double buscarTaxaJurosMensalPorIdade(int idade) {

        log.info("Buscando taxa de juros para a idade: {}", idade);

        if (idade <= 0) {
            throw new BusinessException("Idade do cliente inválida");
        }

        var taxaJurosEntity = taxaJurosRepository
                .findAllByIdadeInicialLessThanEqualAndIdadeFinalGreaterThanEqual(idade, idade)
                .orElseThrow(() -> new BusinessException("Taxa de juros não encontrada para a idade: " + idade));

        log.info("Taxa de juros anual encontrada: {}", taxaJurosEntity);

        var taxaJuros = taxaJurosEntity.toModel();

        return taxaJuros.calcularTaxaJurosMensal();
    }
}
