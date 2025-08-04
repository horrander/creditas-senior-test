package br.com.creditas.simulador_emprestimo.service;

import br.com.creditas.simulador_emprestimo.model.Cliente;

public interface ClienteService {

    /**
     * Busca um cliente pelo CPF.
     *
     * @param cpf CPF do cliente a ser buscado.
     * @return Cliente encontrado ou null se não existir.
     */
    public Cliente buscarClientePorCpf(String cpf);
}
