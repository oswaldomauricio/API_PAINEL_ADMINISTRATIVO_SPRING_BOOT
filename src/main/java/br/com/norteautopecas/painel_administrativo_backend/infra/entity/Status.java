package br.com.norteautopecas.painel_administrativo_backend.infra.entity;

public enum Status {
    NOVO("Novo"),
    PENDENTE("Pendente"),
    RECEBIDO("Recebido"),
    ANALISE("Em Análise"),
    AGUARDANDO_CLIENTE("Aguardando Cliente"),
    AGUARDANDO_FORNECEDOR("Aguardando Fornecedor"),
    AGUARDANDO_TRANSPORTE("Aguardando Transporte"),
    AGUARDANDO_PECA("Aguardando Peça"),
    AGUARDANDO_APROVACAO("Aguardando Aprovação"),
    AGUARDANDO_RETORNO("Aguardando Retorno"),
    APROVADO("Aprovado"),
    REPROVADO("Reprovado"),
    RESOLVIDO("Resolvido"),
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
