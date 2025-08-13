package br.com.norteautopecas.painel_administrativo_backend.infra.entity;

public enum Status {
    NOVO("Novo"),
    PENDENTE("Pendente"),
    REPROVADO("Reprovado"),
    RESOLVIDO("Aprovado"),
    CANCELADO("Cancelado"),
    CONCLUIDO("Concluído");

    private final String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
