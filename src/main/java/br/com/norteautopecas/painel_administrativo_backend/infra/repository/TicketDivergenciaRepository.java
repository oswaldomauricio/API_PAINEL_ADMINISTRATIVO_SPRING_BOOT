package br.com.norteautopecas.painel_administrativo_backend.infra.repository;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketDivergencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketDivergenciaRepository extends JpaRepository<TicketDivergencia, Long> {
}
