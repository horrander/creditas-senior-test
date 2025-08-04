package br.com.creditas.simulador_emprestimo.entity;

import br.com.creditas.simulador_emprestimo.model.TaxaJuros;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "taxa_juros")
public class TaxaJurosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "idade_inicial", nullable = false)
    private int idadeInicial;

    @Column(name = "idade_final", nullable = false)
    private int idadeFinal;

    @Column(name = "taxa_anual", nullable = false)
    private double taxaAnual;

    public TaxaJuros toModel() {

        return TaxaJuros.builder()
                .id(getId())
                .idadeInicial(getIdadeInicial())
                .idadeFinal(getIdadeFinal())
                .taxaAnual(getTaxaAnual())
                .build();
    }
}
