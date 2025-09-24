package br.com.norteautopecas.painel_administrativo_backend.controllers;

import br.com.norteautopecas.painel_administrativo_backend.bussines.TicketGarantiaService;
import br.com.norteautopecas.painel_administrativo_backend.bussines.TicketMessageService;
import br.com.norteautopecas.painel_administrativo_backend.bussines.TicketStatusHistoricoGarantiaService;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.garantia.TicketGarantiaCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.garantia.TicketGarantiaDetailsDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.garantia.TicketGarantiaFilterDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.message.TicketMessageCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.message.TicketMessageDetailsDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.status_historico.TicketStatusHistoricoCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.status_historico.TicketStatusHistoricoDetailsDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.status_historico.TicketStatusHistoricoListByIdDTO;
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
    @Autowired
    private TicketStatusHistoricoGarantiaService ticketStatusHistoricoGarantiaService;

    @PostMapping
    @Transactional
    public ResponseEntity<TicketGarantiaDetailsDTO> cadastrarTicket(@RequestBody @Valid TicketGarantiaCreateDTO dados) {
        TicketGarantiaDetailsDTO ticketGarantiaDetails = ticketGarantiaService.cadastrarTicket(dados);

        var location = URI.create("/v1/ticket-garantia/" + ticketGarantiaDetails.id());

        return ResponseEntity.created(location).body(ticketGarantiaDetails);

    }

    @GetMapping
    public ResponseEntity<Page<TicketGarantiaDetailsDTO>> consultarTicketsPassandoFiltros(
            TicketGarantiaFilterDTO filtro,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<TicketGarantiaDetailsDTO> ticketsPaginados =
                ticketGarantiaService.buscarTodosTickets(filtro, pageable);
        return ResponseEntity.ok(ticketsPaginados);
    }

    //listar mensagens por id do ticket de garantia
    @GetMapping({"/mensagem/{id}"})
    public ResponseEntity<List<TicketMessageDetailsDTO>> listarMensagensPorIdDeGarantia(@PathVariable Long id) {
        return ResponseEntity.ok(ticketMessage.listarMensagensGarantia(id));
    }

    //envio de mensagem no ticket de garantia
    @PostMapping("/mensagem")
    @Transactional
    public ResponseEntity<TicketMessageDetailsDTO> enviarMensagemPorTicketGarantia(@RequestBody @Valid TicketMessageCreateDTO dados) {

        TicketMessageDetailsDTO ticketMessageDetails = ticketMessage.adicionarMensagemGarantia(dados);

        var location = URI.create("/v1/ticket-garantia/mensagem" + ticketMessageDetails.ticketId());

        return ResponseEntity.created(location).body(ticketMessageDetails);

    }

    //atualizar o status do ticket de garantia
    @PostMapping("/status/atualizar")
    public ResponseEntity<TicketStatusHistoricoDetailsDTO> atualizarStatus(
            @RequestBody TicketStatusHistoricoCreateDTO dados
    ) {
        TicketStatusHistoricoDetailsDTO result =
                ticketStatusHistoricoGarantiaService.atualizarStatusDeTicket(dados);
        return ResponseEntity.ok(result);
    }

    //Listar o histórico de status do ticket de garantia
    @GetMapping("/status/historico")
    public ResponseEntity<List<TicketStatusHistoricoDetailsDTO>> listarHistorico(@RequestParam Long ticketId) {
        List<TicketStatusHistoricoDetailsDTO> historicos =
                ticketStatusHistoricoGarantiaService.listarStatusDeTicket(
                        new TicketStatusHistoricoListByIdDTO(ticketId)
                );

        if (historicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historicos);
    }

}
