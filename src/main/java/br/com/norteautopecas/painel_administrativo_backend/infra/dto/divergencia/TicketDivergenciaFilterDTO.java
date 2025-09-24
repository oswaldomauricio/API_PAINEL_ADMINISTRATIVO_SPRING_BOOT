package br.com.norteautopecas.painel_administrativo_backend.infra.dto.divergencia;

import br.com.norteautopecas.painel_administrativo_backend.infra.enums.StatusDivergencia;
import br.com.norteautopecas.painel_administrativo_backend.infra.enums.StatusGarantia;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record TicketDivergenciaFilterDTO(
        Long ticketId,
        String fornecedor,
        String cpfCnpj,
        String nota,
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        LocalDate dataInicio,
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        LocalDate dataFim,
        StatusDivergencia status,
        Integer loja
) {
}
