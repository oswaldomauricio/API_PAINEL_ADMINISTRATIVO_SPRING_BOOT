package br.com.norteautopecas.painel_administrativo_backend.infra.dto.garantia;

import br.com.norteautopecas.painel_administrativo_backend.infra.enums.StatusGarantia;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record TicketGarantiaFilterDTO(
        Long ticketId,
        String fornecedor,
        String nomeCliente,
        String cpfCnpj,
        String nota,
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        LocalDate dataInicio,
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        LocalDate dataFim,
        StatusGarantia status,
        Integer loja
) {
}
