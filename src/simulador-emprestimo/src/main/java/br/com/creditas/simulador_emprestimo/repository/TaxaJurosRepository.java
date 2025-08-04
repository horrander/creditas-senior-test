package br.com.creditas.simulador_emprestimo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.creditas.simulador_emprestimo.entity.TaxaJurosEntity;

public interface TaxaJurosRepository extends JpaRepository<TaxaJurosEntity, Integer> {

    /**
     * Busca a taxa de juros aplicável para uma determinada idade.
     *
     * @param idade Idade do cliente.
     * @return Taxa de juros encontrada ou null se não existir.
     */
    Optional<TaxaJurosEntity> findAllByIdadeInicialLessThanEqualAndIdadeFinalGreaterThanEqual(int idadeInicial,
            int idadeFinal);
}
