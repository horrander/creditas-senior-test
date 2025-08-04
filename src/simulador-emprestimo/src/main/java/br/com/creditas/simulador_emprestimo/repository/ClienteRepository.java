package br.com.creditas.simulador_emprestimo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.creditas.simulador_emprestimo.entity.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {

    /**
     * Busca um cliente pelo CPF.
     *
     * @param cpf CPF do cliente a ser buscado.
     * @return Cliente encontrado ou null se não existir.
     */
    Optional<ClienteEntity> findByCpf(String cpf);

}
