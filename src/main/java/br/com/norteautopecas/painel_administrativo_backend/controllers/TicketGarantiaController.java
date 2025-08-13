package br.com.norteautopecas.painel_administrativo_backend.controllers;

import br.com.norteautopecas.painel_administrativo_backend.bussines.TicketGarantiaService;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketGarantiaCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketGarantiaDetailsDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/ticket-garantia")
@SecurityRequirement(name = "bearer-key")
public class TicketGarantiaController {

    @Autowired
    private TicketGarantiaService ticketGarantiaService;

    @PostMapping
    @Transactional
    public ResponseEntity<TicketGarantiaDetailsDTO> cadastrarTicket(@RequestBody @Valid TicketGarantiaCreateDTO dados) {
        TicketGarantiaDetailsDTO ticketGarantiaDetails = ticketGarantiaService.cadastrarTicket(dados);

        var location = URI.create("/v1/ticket-garantia/" + ticketGarantiaDetails.id());

        return ResponseEntity.created(location).body(ticketGarantiaDetails);

    }
}
