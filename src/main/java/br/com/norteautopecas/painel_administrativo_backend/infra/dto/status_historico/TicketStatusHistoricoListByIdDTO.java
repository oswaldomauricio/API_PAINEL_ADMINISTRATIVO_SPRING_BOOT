package br.com.norteautopecas.painel_administrativo_backend.infra.dto.status_historico;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

public record TicketStatusHistoricoListByIdDTO(
        @NotNull
        @JsonAlias({"ticket_id", "ticketId"})
        Long ticketId
) {
}
