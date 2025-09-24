package br.com.norteautopecas.painel_administrativo_backend.infra.repository;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StoreInformation;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketDivergencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TicketDivergenciaRepository extends JpaRepository<TicketDivergencia, Long>, JpaSpecificationExecutor<TicketDivergencia> {
    Page<TicketDivergencia> findByTicket_Loja(StoreInformation storeInformation, Pageable pageable);
}
