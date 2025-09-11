package br.com.norteautopecas.painel_administrativo_backend.infra.repository;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketMessageRepository extends JpaRepository<TicketMessage,
        Long> {
    List<TicketMessage> findByTicketGarantiaId(Long ticketGarantiaId);

    List<TicketMessage> findByTicketDivergenciaId(Long ticketDivergenciaId);
}
