package br.com.norteautopecas.painel_administrativo_backend.infra.repository;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StoreInformation;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketDivergencia;
import br.com.norteautopecas.painel_administrativo_backend.infra.enums.StatusDivergencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TicketDivergenciaRepository extends JpaRepository<TicketDivergencia, Long>, JpaSpecificationExecutor<TicketDivergencia> {
    Page<TicketDivergencia> findByTicket_Loja(StoreInformation storeInformation, Pageable pageable);

    Integer countByTicket_Loja(StoreInformation loja);

    Integer countByTicket_LojaAndStatus(StoreInformation loja,
                                        StatusDivergencia status);

    Integer countByTicket_LojaAndStatusNotIn(StoreInformation loja,
                                             List<StatusDivergencia> status);
}
