package br.com.norteautopecas.painel_administrativo_backend.controllers;

import br.com.norteautopecas.painel_administrativo_backend.bussines.TicketGarantiaService;
import br.com.norteautopecas.painel_administrativo_backend.bussines.TicketMessageService;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketGarantiaCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketGarantiaDetailsDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketMessageCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketMessageDetailsDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/ticket-garantia")
@SecurityRequirement(name = "bearer-key")
public class TicketGarantiaController {

    @Autowired
    private TicketGarantiaService ticketGarantiaService;
    @Autowired
    private TicketMessageService ticketMessage;

    @PostMapping
    @Transactional
    public ResponseEntity<TicketGarantiaDetailsDTO> cadastrarTicket(@RequestBody @Valid TicketGarantiaCreateDTO dados) {
        TicketGarantiaDetailsDTO ticketGarantiaDetails = ticketGarantiaService.cadastrarTicket(dados);

        var location = URI.create("/v1/ticket-garantia/" + ticketGarantiaDetails.id());

        return ResponseEntity.created(location).body(ticketGarantiaDetails);

    }

    @GetMapping
    public ResponseEntity<Page<TicketGarantiaDetailsDTO>> consultarTickets(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<TicketGarantiaDetailsDTO> ticketsPaginados = ticketGarantiaService.buscarTodosTickets(pageable);
        return ResponseEntity.ok(ticketsPaginados);
    }

    @GetMapping("/loja/{loja}")
    public ResponseEntity<Page<TicketGarantiaDetailsDTO>> consultarTicketsPorLoja(
            @PathVariable Integer loja,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(ticketGarantiaService.buscarTicketsPorLoja(loja, pageable));
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<TicketGarantiaDetailsDTO> consultarTicketPorId(@PathVariable Long id) {
        return ticketGarantiaService.buscarTicketPorId(id);
    }

    @GetMapping({"/mensagem/{id}"})
    public ResponseEntity<List<TicketMessageDetailsDTO>> listarMensagensPorIdDeGarantia(@PathVariable Long id) {
        return ResponseEntity.ok(ticketMessage.listarMensagensGarantia(id));
    }


    @PostMapping("/mensagem")
    @Transactional
    public ResponseEntity<TicketMessageDetailsDTO> enviarMensagemPorTicketGarantia(@RequestBody @Valid TicketMessageCreateDTO dados) {

        TicketMessageDetailsDTO ticketMessageDetails = ticketMessage.adicionarMensagemGarantia(dados);

        var location = URI.create("/v1/ticket-garantia/mensagem" + ticketMessageDetails.ticketId());

        return ResponseEntity.created(location).body(ticketMessageDetails);

    }

}
