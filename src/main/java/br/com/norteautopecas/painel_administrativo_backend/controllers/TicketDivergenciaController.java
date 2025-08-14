package br.com.norteautopecas.painel_administrativo_backend.controllers;

import br.com.norteautopecas.painel_administrativo_backend.bussines.TicketDivergenciaService;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketDivergenciaCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketDivergenciaDetailsDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Page<TicketDivergenciaDetailsDTO>> consultarTickets(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<TicketDivergenciaDetailsDTO> ticketsPaginados =
                ticketDivergenciaService.buscarTodosTickets(pageable);
        return ResponseEntity.ok(ticketsPaginados);
    }

    @GetMapping("/loja/{loja}")
    public ResponseEntity<Page<TicketDivergenciaDetailsDTO>> consultarTicketsPorLoja(
            @PathVariable Integer loja,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(ticketDivergenciaService.buscarTicketsPorLoja(loja, pageable));
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<TicketDivergenciaDetailsDTO> consultarTicketPorId(@PathVariable Long id) {
        return ticketDivergenciaService.buscarTicketPorId(id);
    }
}
