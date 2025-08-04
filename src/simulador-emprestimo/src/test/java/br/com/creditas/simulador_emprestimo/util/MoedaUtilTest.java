package br.com.creditas.simulador_emprestimo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MoedaUtilTest {

    @Test
    void testFormatarMoedaBRL() {
        String resultado = MoedaUtil.formatarMoeda("BRL", 1234.56);
        assertEquals("R$ 1.234,56", resultado);
    }

    @Test
    void testFormatarMoedaUSD() {
        String resultado = MoedaUtil.formatarMoeda("USD", 9876.50);
        assertEquals("$ 9.876,50", resultado);
    }

    @Test
    void testFormatarMoedaEUR() {
        String resultado = MoedaUtil.formatarMoeda("EUR", 100.10);
        assertEquals("€ 100,10", resultado);
    }

    @Test
    void testFormatarMoedaValorZero() {
        String resultado = MoedaUtil.formatarMoeda("BRL", 0.00);
        assertEquals("R$ 0,00", resultado);
    }

    @Test
    void testFormatarMoedaValorNegativo() {
        String resultado = MoedaUtil.formatarMoeda("USD", -50.75);
        assertEquals("$ -50,75", resultado);
    }

    @Test
    void testFormatarMoedaValorComMaisDeDuasCasasDecimais() {
        String resultado = MoedaUtil.formatarMoeda("EUR", 123.4567);
        assertEquals("€ 123,46", resultado);
    }

    @Test
    void testFormatarMoedaCodigoDesconhecido() {
        String resultado = MoedaUtil.formatarMoeda("ABC", 42.42);
        assertEquals("R$ 42,42", resultado);
    }
}
