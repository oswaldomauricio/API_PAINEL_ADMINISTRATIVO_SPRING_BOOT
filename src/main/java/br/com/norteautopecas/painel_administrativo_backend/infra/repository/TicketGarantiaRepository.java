package br.com.norteautopecas.painel_administrativo_backend.infra.repository;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketGarantia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketGarantiaRepository extends JpaRepository<TicketGarantia, Long> {
}
