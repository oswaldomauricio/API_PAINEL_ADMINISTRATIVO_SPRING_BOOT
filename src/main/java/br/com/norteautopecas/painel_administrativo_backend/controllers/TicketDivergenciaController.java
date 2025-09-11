package br.com.norteautopecas.painel_administrativo_backend.controllers;

import br.com.norteautopecas.painel_administrativo_backend.bussines.TicketDivergenciaService;
import br.com.norteautopecas.painel_administrativo_backend.bussines.TicketMessageService;
import br.com.norteautopecas.painel_administrativo_backend.bussines.TicketStatusHistoricoDivergenciaService;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.*;
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
import java.util.List;

@RestController
@RequestMapping("/v1/ticket-divergencia")
@SecurityRequirement(name = "bearer-key")
public class TicketDivergenciaController {
    @Autowired
    private TicketDivergenciaService ticketDivergenciaService;
    @Autowired
    private TicketMessageService ticketMessage;
    @Autowired
    private TicketStatusHistoricoDivergenciaService ticketStatusHistoricoDivergenciaService;

    @PostMapping
    @Transactional
    public ResponseEntity<TicketDivergenciaDetailsDTO> cadastrarTicket(@RequestBody @Valid TicketDivergenciaCreateDTO dados) {
        TicketDivergenciaDetailsDTO ticketDivergenciaDetails = ticketDivergenciaService.cadastrarTicket(dados);

        var location = URI.create("/v1/ticket-divergencia/" + ticketDivergenciaDetails.id());

        return ResponseEntity.created(location).body(ticketDivergenciaDetails);

    }

    @GetMapping
    public ResponseEntity<Page<TicketDivergenciaDetailsDTO>> consultarTickets(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<TicketDivergenciaDetailsDTO> ticketsPaginados = ticketDivergenciaService.buscarTodosTickets(pageable);
        return ResponseEntity.ok(ticketsPaginados);
    }

    @GetMapping("/loja/{loja}")
    public ResponseEntity<Page<TicketDivergenciaDetailsDTO>> consultarTicketsPorLoja(@PathVariable Integer loja, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(ticketDivergenciaService.buscarTicketsPorLoja(loja, pageable));
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<TicketDivergenciaDetailsDTO> consultarTicketPorId(@PathVariable Long id) {
        return ticketDivergenciaService.buscarTicketPorId(id);
    }

    //listar mensagens por id do ticket de garantia
    @GetMapping({"/mensagem/{id}"})
    public ResponseEntity<List<TicketMessageDetailsDTO>> listarMensagensPorIdDeDivergencia(@PathVariable Long id) {
        return ResponseEntity.ok(ticketMessage.listarMensagensDivergencia(id));
    }


    //envio de mensagem no ticket de divergencia
    @PostMapping("/mensagem")
    @Transactional
    public ResponseEntity<TicketMessageDetailsDTO> enviarMensagemPorTicketDivergencia(@RequestBody @Valid TicketMessageCreateDTO dados) {

        TicketMessageDetailsDTO ticketMessageDetails = ticketMessage.adicionarMensagemDivergencia(dados);

        var location = URI.create("/v1/ticket-divergencia/mensagem" + ticketMessageDetails.ticketId());

        return ResponseEntity.created(location).body(ticketMessageDetails);

    }

    //atualizar o status do ticket de garantia
    @PostMapping("/status/atualizar")
    public ResponseEntity<TicketStatusHistoricoDetailsDTO> atualizarStatus(@RequestBody TicketStatusHistoricoCreateDTO dados) {
        TicketStatusHistoricoDetailsDTO result = ticketStatusHistoricoDivergenciaService.atualizarStatusDeTicket(dados);
        return ResponseEntity.ok(result);
    }

    //Listar o histórico de status do ticket de garantia
    @GetMapping("/status/historico")
    public ResponseEntity<List<TicketStatusHistoricoDetailsDTO>> listarHistorico(@RequestParam Long ticketId) {
        List<TicketStatusHistoricoDetailsDTO> historicos = ticketStatusHistoricoDivergenciaService.listarStatusDeTicket(new TicketStatusHistoricoListByIdDTO(ticketId));

        if (historicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historicos);
    }
}
