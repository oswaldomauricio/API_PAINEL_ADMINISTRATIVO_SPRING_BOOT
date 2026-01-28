package br.com.norteautopecas.painel_administrativo_backend.infra.enums.caixa;

import java.util.Arrays;

public enum TipoOperacao {
    ENTRADA("Entrada"),

    SAIDA("Saida");

    private final String tipoOperacao;

    TipoOperacao(String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public static TipoOperacao fromString(String valor) {
        return Arrays.stream(TipoOperacao.values())
                .filter(t -> t.name().equalsIgnoreCase(valor))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Tipo de operação inválido: " + valor)
                );
    }

}
