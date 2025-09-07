package br.com.norteautopecas.painel_administrativo_backend.infra.dto;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Status;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.User;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketStatusHistoricoCreateDTO(
        @JsonAlias({"ticket_id", "ticketId", "id_ticket"})
        @NotNull
        Long ticketId,

        @NotBlank
        @JsonAlias({"status_novo", "status", "statusNovo"})
        Status status,

        String ticketTipo,

        @NotBlank
        String mensagem,

        @NotNull
        @JsonAlias({"id_user", "idUser"})
        Long idUser
) {
}
