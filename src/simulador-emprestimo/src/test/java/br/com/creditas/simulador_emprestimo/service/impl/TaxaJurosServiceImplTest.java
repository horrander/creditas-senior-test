package br.com.creditas.simulador_emprestimo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.creditas.simulador_emprestimo.entity.TaxaJurosEntity;
import br.com.creditas.simulador_emprestimo.exception.BusinessException;
import br.com.creditas.simulador_emprestimo.model.TaxaJuros;
import br.com.creditas.simulador_emprestimo.repository.TaxaJurosRepository;

class TaxaJurosServiceImplTest {

    private TaxaJurosRepository taxaJurosRepository;
    private TaxaJurosServiceImpl taxaJurosService;

    @BeforeEach
    void setUp() {
        taxaJurosRepository = mock(TaxaJurosRepository.class);
        taxaJurosService = new TaxaJurosServiceImpl(taxaJurosRepository);
    }

    @Test
    void buscarTaxaJurosMensalPorIdade_DeveRetornarTaxaQuandoEncontrada() {
        int idade = 30;
        double taxaMensalEsperada = 0.015;

        TaxaJurosEntity entityMock = mock(TaxaJurosEntity.class);
        TaxaJuros taxaJurosMock = mock(TaxaJuros.class);

        when(taxaJurosRepository.findAllByIdadeInicialLessThanEqualAndIdadeFinalGreaterThanEqual(idade, idade))
                .thenReturn(Optional.of(entityMock));
        when(entityMock.toModel()).thenReturn(taxaJurosMock);
        when(taxaJurosMock.calcularTaxaJurosMensal()).thenReturn(taxaMensalEsperada);

        Double taxaMensal = taxaJurosService.buscarTaxaJurosMensalPorIdade(idade);

        assertEquals(taxaMensalEsperada, taxaMensal);
        verify(taxaJurosRepository).findAllByIdadeInicialLessThanEqualAndIdadeFinalGreaterThanEqual(idade, idade);
        verify(entityMock).toModel();
        verify(taxaJurosMock).calcularTaxaJurosMensal();
    }

    @Test
    void buscarTaxaJurosMensalPorIdade_DeveLancarExcecaoQuandoIdadeInvalida() {
        int idadeInvalida = 0;

        BusinessException exception = assertThrows(BusinessException.class,
                () -> taxaJurosService.buscarTaxaJurosMensalPorIdade(idadeInvalida));

        assertEquals("Idade do cliente inválida", exception.getMessage());
        verifyNoInteractions(taxaJurosRepository);
    }

    @Test
    void buscarTaxaJurosMensalPorIdade_DeveLancarExcecaoQuandoTaxaNaoEncontrada() {
        int idade = 50;

        when(taxaJurosRepository.findAllByIdadeInicialLessThanEqualAndIdadeFinalGreaterThanEqual(idade, idade))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> taxaJurosService.buscarTaxaJurosMensalPorIdade(idade));

        assertEquals("Taxa de juros não encontrada para a idade: " + idade, exception.getMessage());
        verify(taxaJurosRepository).findAllByIdadeInicialLessThanEqualAndIdadeFinalGreaterThanEqual(idade, idade);
    }
}
