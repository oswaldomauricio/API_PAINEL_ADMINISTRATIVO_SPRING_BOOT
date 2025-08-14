package br.com.norteautopecas.painel_administrativo_backend.infra.repository;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StoreInformation;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketDivergencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketDivergenciaRepository extends JpaRepository<TicketDivergencia, Long> {
    Page<TicketDivergencia> findByTicket_Loja(StoreInformation storeInformation, Pageable pageable);
}
