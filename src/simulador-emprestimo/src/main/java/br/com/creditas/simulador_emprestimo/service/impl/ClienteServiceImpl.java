package br.com.creditas.simulador_emprestimo.service.impl;

import org.springframework.stereotype.Service;

import br.com.creditas.simulador_emprestimo.exception.BusinessException;
import br.com.creditas.simulador_emprestimo.model.Cliente;
import br.com.creditas.simulador_emprestimo.repository.ClienteRepository;
import br.com.creditas.simulador_emprestimo.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public Cliente buscarClientePorCpf(String cpf) {

        log.info("Buscando cliente pelo CPF: {}", cpf);

        var clienteEntity = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new BusinessException("Cliente não encontrado com CPF: " + cpf));

        log.info("Cliente encontrado: {}", clienteEntity);

        return clienteEntity.toModel();
    }
}
