package br.com.norteautopecas.painel_administrativo_backend.controllers;

import br.com.norteautopecas.painel_administrativo_backend.bussines.TicketDivergenciaService;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketDivergenciaCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketDivergenciaDetailsDTO;
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
@RequestMapping("/v1/ticket-divergencia")
@SecurityRequirement(name = "bearer-key")
public class TicketDivergenciaController {
    @Autowired
    private TicketDivergenciaService ticketDivergenciaService;

    @PostMapping
    @Transactional
    public ResponseEntity<TicketDivergenciaDetailsDTO> cadastrarTicket(@RequestBody @Valid TicketDivergenciaCreateDTO dados) {
        TicketDivergenciaDetailsDTO ticketDivergenciaDetails =
                ticketDivergenciaService.cadastrarTicket(dados);

        var location =
                URI.create("/v1/ticket-divergencia/" + ticketDivergenciaDetails.id());

        return ResponseEntity.created(location).body(ticketDivergenciaDetails);

    }
}
