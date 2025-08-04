package br.com.creditas.simulador_emprestimo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.creditas.simulador_emprestimo.entity.ClienteEntity;
import br.com.creditas.simulador_emprestimo.exception.BusinessException;
import br.com.creditas.simulador_emprestimo.model.Cliente;
import br.com.creditas.simulador_emprestimo.repository.ClienteRepository;

class ClienteServiceImplTest {

    private ClienteRepository clienteRepository;
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        clienteRepository = mock(ClienteRepository.class);
        clienteService = new ClienteServiceImpl(clienteRepository);
    }

    @Test
    void buscarClientePorCpf_DeveRetornarCliente_QuandoEncontrado() {

        String cpf = "12345678900";
        Cliente cliente = mock(Cliente.class);
        ClienteEntity clienteEntity = mock(ClienteEntity.class);

        when(clienteEntity.toString()).thenReturn("ClienteEntity");
        when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.of(clienteEntity));
        when(clienteEntity.toModel()).thenReturn(cliente);

        Cliente resultado = clienteService.buscarClientePorCpf(cpf);

        assertNotNull(resultado);
        assertEquals(cliente, resultado);
        verify(clienteRepository, times(1)).findByCpf(cpf);
    }

    @Test
    void buscarClientePorCpf_DeveLancarBusinessException_QuandoNaoEncontrado() {

        String cpf = "00000000000";
        when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            clienteService.buscarClientePorCpf(cpf);
        });

        assertEquals("Cliente não encontrado com CPF: " + cpf, exception.getMessage());
        verify(clienteRepository, times(1)).findByCpf(cpf);
    }
}
