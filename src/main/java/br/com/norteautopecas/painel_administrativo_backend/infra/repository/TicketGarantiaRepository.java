package br.com.norteautopecas.painel_administrativo_backend.infra.repository;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StoreInformation;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketGarantia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketGarantiaRepository extends JpaRepository<TicketGarantia, Long> {
    Page<TicketGarantia> findByTicket_Loja(StoreInformation loja, Pageable pageable);
}
