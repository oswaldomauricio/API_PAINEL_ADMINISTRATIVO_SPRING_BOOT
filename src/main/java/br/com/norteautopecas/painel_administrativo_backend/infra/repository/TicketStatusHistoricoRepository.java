package br.com.norteautopecas.painel_administrativo_backend.infra.repository;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketDivergencia;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketGarantia;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketStatusHistorico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketStatusHistoricoRepository extends JpaRepository<TicketStatusHistorico, Long> {
    List<TicketStatusHistorico> findByTicketDivergenciaIdOrderByDataAtualizacaoDesc(Long ticketId);

    List<TicketStatusHistorico> findByTicketGarantiaIdOrderByDataAtualizacaoDesc(Long ticketId);

    List<TicketStatusHistorico> findByTicketGarantia(TicketGarantia ticketGarantia);

    List<TicketStatusHistorico> findByTicketDivergencia(TicketDivergencia ticketDivergencia);
}
