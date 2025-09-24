package br.com.norteautopecas.painel_administrativo_backend.infra.dto.status_historico;

public record TicketStatusHistoricoDetailsDTO(
        Long ticketId,
        String statusNovo,
        String mensagem,
        String alteradoPor,
        String dataAtualizacao
) {
}
