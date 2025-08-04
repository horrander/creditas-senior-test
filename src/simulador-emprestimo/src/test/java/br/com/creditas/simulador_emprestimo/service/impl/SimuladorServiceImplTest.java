package br.com.creditas.simulador_emprestimo.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.creditas.simulador_emprestimo.dto.SimulacaoRequestDto;
import br.com.creditas.simulador_emprestimo.dto.SimulacaoResponseDto;
import br.com.creditas.simulador_emprestimo.model.Cliente;
import br.com.creditas.simulador_emprestimo.service.ClienteService;
import br.com.creditas.simulador_emprestimo.service.TaxaJurosService;

class SimuladorServiceImplTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private TaxaJurosService taxaJurosService;

    @InjectMocks
    private SimuladorServiceImpl simuladorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void simularEmprestimoLote_deveRetornarListaDeSimulacaoResponseDto() throws Exception {
        // Arrange
        SimulacaoRequestDto req1 = mock(SimulacaoRequestDto.class);
        SimulacaoRequestDto req2 = mock(SimulacaoRequestDto.class);
        SimulacaoResponseDto resp1 = mock(SimulacaoResponseDto.class);
        SimulacaoResponseDto resp2 = mock(SimulacaoResponseDto.class);

        SimuladorServiceImpl spyService = Mockito.spy(simuladorService);
        doReturn(resp1).when(spyService).simularEmprestimo(req1);
        doReturn(resp2).when(spyService).simularEmprestimo(req2);

        List<SimulacaoRequestDto> requests = List.of(req1, req2);

        // Act
        CompletableFuture<List<SimulacaoResponseDto>> future = spyService.simularEmprestimoLote(requests);
        List<SimulacaoResponseDto> result = future.get();

        // Assert
        assertThat(result).containsExactly(resp1, resp2);
        verify(spyService).simularEmprestimo(req1);
        verify(spyService).simularEmprestimo(req2);
    }

    @Test
    void simularEmprestimo_deveRetornarSimulacaoResponseDtoCorreto() {

        // Arrange
        SimulacaoRequestDto requestDto = new SimulacaoRequestDto();
        requestDto.setCpfCliente("12345678901");
        requestDto.setValorSolicitado(10000);
        requestDto.setNumeroParcelas(12);
        requestDto.setCodigoAlfabeticoMoeda("BRL");

        Cliente cliente = Cliente.builder()
                .cpf("12345678901")
                .dataNascimento(LocalDate.of(1990, Month.SEPTEMBER, 27))
                .build();

        Mockito.when(clienteService.buscarClientePorCpf(Mockito.anyString()))
                .thenReturn(cliente);

        Mockito.when(taxaJurosService.buscarTaxaJurosMensalPorIdade(Mockito.anyInt()))
                .thenReturn(anyDouble());

        // Act
        SimulacaoResponseDto response = simuladorService.simularEmprestimo(requestDto);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getValorParcela()).hasSizeGreaterThan(0);
        assertThat(response.getValorTotalJuros()).hasSizeGreaterThan(0);
        assertThat(response.getValorTotalPagamento()).hasSizeGreaterThan(0);
    }

    @Test
    void simularEmprestimo_deveLancarExcecaoQuandoDadosInvalidos() {

        // Arrange
        SimulacaoRequestDto requestDto = new SimulacaoRequestDto();
        requestDto.setCpfCliente("12345678901");
        requestDto.setValorSolicitado(-1);
        requestDto.setNumeroParcelas(12);
        requestDto.setCodigoAlfabeticoMoeda("BRL");

        // Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            requestDto.toModel();
        });
    }

    @Test
    void simularEmprestimo_deveLancarExcecaoQuandoDadosObrigatoriosNaoInformados() {

        // Arrange
        SimulacaoRequestDto requestDto = new SimulacaoRequestDto();
        requestDto.setCpfCliente(null);
        requestDto.setValorSolicitado(-1);
        requestDto.setNumeroParcelas(12);
        requestDto.setCodigoAlfabeticoMoeda("BRL");

        // Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            requestDto.toModel();
        });
    }

    @Test
    void simularEmprestimo_deveLancarExcecaoQuandoClienteNaoEncontrado() {

        // Arrange
        SimulacaoRequestDto requestDto = new SimulacaoRequestDto();
        requestDto.setCpfCliente("12345678901");
        requestDto.setValorSolicitado(10000);
        requestDto.setNumeroParcelas(12);
        requestDto.setCodigoAlfabeticoMoeda("BRL");

        Mockito.when(clienteService.buscarClientePorCpf(Mockito.anyString()))
                .thenReturn(null);

        Mockito.when(taxaJurosService.buscarTaxaJurosMensalPorIdade(Mockito.anyInt()))
                .thenReturn(anyDouble());

        // Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            simuladorService.simularEmprestimo(requestDto);
        });
    }

    @Test
    void simularEmprestimo_deveRetornarExcecaoQuandoTaxaJurosNaoEncontrado() {

        // Arrange
        SimulacaoRequestDto requestDto = new SimulacaoRequestDto();
        requestDto.setCpfCliente("12345678901");
        requestDto.setValorSolicitado(10000);
        requestDto.setNumeroParcelas(12);
        requestDto.setCodigoAlfabeticoMoeda("BRL");

        Cliente cliente = Cliente.builder()
                .cpf("12345678901")
                .dataNascimento(LocalDate.of(1990, Month.SEPTEMBER, 27))
                .build();

        Mockito.when(clienteService.buscarClientePorCpf(Mockito.anyString()))
                .thenReturn(cliente);

        Mockito.when(taxaJurosService.buscarTaxaJurosMensalPorIdade(Mockito.anyInt()))
                .thenReturn(null);

        // Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            simuladorService.simularEmprestimo(requestDto);
        });
    }
}
