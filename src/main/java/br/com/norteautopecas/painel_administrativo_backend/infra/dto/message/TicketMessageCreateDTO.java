package br.com.norteautopecas.painel_administrativo_backend.infra.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketMessageCreateDTO(
        @NotNull
        Long ticketId,

        @NotNull
        Long id_usuario,

        @NotBlank
        String msg,

        @NotNull
        boolean internal
) {
}
