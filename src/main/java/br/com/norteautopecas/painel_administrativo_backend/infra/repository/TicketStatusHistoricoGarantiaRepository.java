package br.com.norteautopecas.painel_administrativo_backend.infra.repository;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketGarantia;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketStatusHistoricoGarantia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketStatusHistoricoGarantiaRepository extends JpaRepository<TicketStatusHistoricoGarantia, Long> {
    List<TicketStatusHistoricoGarantia> findByTicketGarantiaIdOrderByDataAtualizacaoDesc(Long ticketGarantiaId);

    List<TicketStatusHistoricoGarantia> findByTicketGarantia(TicketGarantia ticketGarantia);

}
