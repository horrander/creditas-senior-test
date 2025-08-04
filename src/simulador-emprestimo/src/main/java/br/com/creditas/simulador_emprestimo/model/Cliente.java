package br.com.creditas.simulador_emprestimo.model;

import java.time.LocalDate;
import java.time.Period;

import br.com.creditas.simulador_emprestimo.exception.BusinessException;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Cliente {

    private int id;
    private String cpf;
    private String nome;
    private LocalDate dataNascimento;

    /**
     * Calcula a idade do cliente com base na data de nascimento.
     *
     * @return Idade do cliente em anos.
     */
    public int calcularIdadeCliente() {

        if (dataNascimento == null) {
            throw new BusinessException("Data de nascimento inválida");
        }

        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}
