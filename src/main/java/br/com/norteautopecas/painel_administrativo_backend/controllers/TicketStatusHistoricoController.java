package br.com.norteautopecas.painel_administrativo_backend.controllers;

import br.com.norteautopecas.painel_administrativo_backend.bussines.TicketStatusHistoricoService;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketStatusHistoricoDetailsDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketStatusHistoricoListByIdDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketStatusHistoricoCreateDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/ticket/status")
@SecurityRequirement(name = "bearer-key")
public class TicketStatusHistoricoController {

    @Autowired
    private TicketStatusHistoricoService ticketStatusHistoricoService;

    @PostMapping("/atualizar")
    public ResponseEntity<TicketStatusHistoricoDetailsDTO> atualizarStatus(
            @RequestBody TicketStatusHistoricoCreateDTO dados
    ) {
        TicketStatusHistoricoDetailsDTO result = ticketStatusHistoricoService.atualizarStatusDeTicket(dados);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{ticketTipo}/{ticketId}")
    public ResponseEntity<List<TicketStatusHistoricoDetailsDTO>> listarHistorico(@RequestBody @Valid TicketStatusHistoricoListByIdDTO dados) {
        List<TicketStatusHistoricoDetailsDTO> historicos =
                ticketStatusHistoricoService.listarStatusDeTicket(dados);

        if (historicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historicos);
    }
}
