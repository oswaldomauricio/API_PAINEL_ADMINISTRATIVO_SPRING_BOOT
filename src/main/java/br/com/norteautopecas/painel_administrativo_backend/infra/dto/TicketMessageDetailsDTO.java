package br.com.norteautopecas.painel_administrativo_backend.infra.dto;


import br.com.norteautopecas.painel_administrativo_backend.infra.dto.users.UserRegistrationDataDTO;

import java.time.LocalDateTime;

public record TicketMessageDetailsDTO(
        Long id,
        Long ticketId,
        UserRegistrationDataDTO usuario,
        String msg,
        boolean internal,
        LocalDateTime timestamp
) {
}