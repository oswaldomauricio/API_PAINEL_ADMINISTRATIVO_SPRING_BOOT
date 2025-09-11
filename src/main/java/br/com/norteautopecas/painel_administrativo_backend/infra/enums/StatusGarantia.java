package br.com.norteautopecas.painel_administrativo_backend.infra.enums;

public enum StatusGarantia {
    NOVO("Novo"),
    RECEBIDO("Recebido"),
    PENDENTE("Pendente"),
    EM_ANALISE("Em Análise"),
    CONCLUIDO("Concluído"),
    CANCELADO("Cancelado"),
    IMPROCEDENTE("Improcedente"),
    TRANSPORTE("Em Transporte"),
    EMISSAO_DE_NOTA("Em Emissão de Nota"),
    RESSARCIMENTO("Ressarcimento"),
    RESSARCIDO("Ressarcido"),
    CREDITO_RECEBIDO("Crédito Recebido");

    private final String descricao;

    StatusGarantia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
