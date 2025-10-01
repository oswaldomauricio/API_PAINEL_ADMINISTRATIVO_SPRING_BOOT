package br.com.norteautopecas.painel_administrativo_backend.infra.repository;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StoreInformation;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketGarantia;
import br.com.norteautopecas.painel_administrativo_backend.infra.enums.StatusGarantia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TicketGarantiaRepository extends JpaRepository<TicketGarantia, Long>, JpaSpecificationExecutor<TicketGarantia> {
    Page<TicketGarantia> findByTicket_Loja(StoreInformation loja, Pageable pageable);

    Integer countByTicket_Loja(StoreInformation loja);

    Integer countByTicket_LojaAndStatus(StoreInformation loja,
                                                       StatusGarantia statusGarantia);

    Integer countByTicket_LojaAndStatusNotIn(StoreInformation loja,
                                                            List<StatusGarantia> statusGarantias);
}
