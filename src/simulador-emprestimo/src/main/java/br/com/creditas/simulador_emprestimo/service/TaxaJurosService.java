package br.com.creditas.simulador_emprestimo.service;

public interface TaxaJurosService {

    /**
     * Busca a taxa de juros aplicável para uma determinada idade.
     *
     * @param idade Idade do cliente.
     * @return Taxa de juros encontrada ou null se não existir.
     */
    public Double buscarTaxaJurosMensalPorIdade(int idade);
}
