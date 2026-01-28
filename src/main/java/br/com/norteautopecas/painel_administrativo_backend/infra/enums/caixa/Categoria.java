package br.com.norteautopecas.painel_administrativo_backend.infra.enums.caixa;

import java.util.Arrays;

public enum Categoria {
    RECIBO("Recibo"),
    CFNF("CF/NF"),
    CTE("CTE"),
    TN("TN"),
    DEVOLUCAO("Devolucao"),
    DEPOSITO_PROSSEGUR("Deposito Prossegur");

    private final String categoria;

    Categoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public static Categoria fromString(String valor) {
        return Arrays.stream(Categoria.values())
                .filter(c -> c.name().equalsIgnoreCase(valor))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Categoria inválida: " + valor)
                );
    }

}
