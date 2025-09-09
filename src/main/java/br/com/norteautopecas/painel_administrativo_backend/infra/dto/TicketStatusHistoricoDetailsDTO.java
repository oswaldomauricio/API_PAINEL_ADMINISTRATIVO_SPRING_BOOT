package br.com.norteautopecas.painel_administrativo_backend.infra.dto;

import java.time.LocalDate;
import java.util.Date;

public record TicketStatusHistoricoDetailsDTO(
        Long ticketId,
        String statusNovo,
        String mensagem,
        String alteradoPor,
        String dataAtualizacao
) {
}
