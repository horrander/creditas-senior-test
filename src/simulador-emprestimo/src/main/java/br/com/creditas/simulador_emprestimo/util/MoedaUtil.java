package br.com.creditas.simulador_emprestimo.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MoedaUtil {

    public static String formatarMoeda(String codigoAlfabeticoMoeda, double valor) {

        DecimalFormat format = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        format.setMaximumFractionDigits(2);
        format.setDecimalFormatSymbols(symbols);
        String valorArredondado = format.format(valor);

        return String.join(" ", obterSimboloMoeda(codigoAlfabeticoMoeda), valorArredondado);
    }

    private static String obterSimboloMoeda(String codigoAlfabeticoMoeda) {

        switch (codigoAlfabeticoMoeda) {
            case "BRL":
                return "R$";
            case "USD":
                return "$";
            case "EUR":
                return "€";
            default:
                return "R$";
        }
    }
}
