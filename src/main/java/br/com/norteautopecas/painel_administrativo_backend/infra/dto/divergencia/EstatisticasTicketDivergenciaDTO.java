package br.com.norteautopecas.painel_administrativo_backend.infra.dto.divergencia;

public record EstatisticasTicketDivergenciaDTO(
        Integer totalTickets,
        Integer ticketsAbertos,
        Integer ticketsEmAndamento,
        Integer ticketsConcluidos,
        Integer ticketsCancelados
) {
}
