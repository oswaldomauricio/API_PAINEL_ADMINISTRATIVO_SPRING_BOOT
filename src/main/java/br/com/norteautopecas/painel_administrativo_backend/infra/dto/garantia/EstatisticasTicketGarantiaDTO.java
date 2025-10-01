package br.com.norteautopecas.painel_administrativo_backend.infra.dto.garantia;

public record EstatisticasTicketGarantiaDTO (
        Integer totalTickets,
        Integer ticketsAbertos,
        Integer ticketsEmAndamento,
        Integer ticketsConcluidos
) {
}
