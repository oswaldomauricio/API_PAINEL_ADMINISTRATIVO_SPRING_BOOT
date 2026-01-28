package br.com.norteautopecas.painel_administrativo_backend.infra.repository;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.tickets.TicketDivergencia;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.tickets.TicketStatusHistoricoDivergencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketStatusHistoricoDivergenciaRepository extends JpaRepository<TicketStatusHistoricoDivergencia, Long> {
    List<TicketStatusHistoricoDivergencia> findByTicketDivergenciaIdOrderByDataAtualizacaoDesc(Long ticketDivergenciaId);

    List<TicketStatusHistoricoDivergencia> findByTicketDivergencia(TicketDivergencia ticketDivergencia);
}