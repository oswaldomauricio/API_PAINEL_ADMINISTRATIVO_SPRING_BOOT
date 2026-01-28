package br.com.norteautopecas.painel_administrativo_backend.infra.repository;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.tickets.TicketGarantia;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.tickets.TicketStatusHistoricoGarantia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketStatusHistoricoGarantiaRepository extends JpaRepository<TicketStatusHistoricoGarantia, Long> {
    List<TicketStatusHistoricoGarantia> findByTicketGarantiaIdOrderByDataAtualizacaoDesc(Long ticketGarantiaId);

    List<TicketStatusHistoricoGarantia> findByTicketGarantia(TicketGarantia ticketGarantia);

}
