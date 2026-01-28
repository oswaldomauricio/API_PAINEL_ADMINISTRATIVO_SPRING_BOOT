package br.com.norteautopecas.painel_administrativo_backend.infra.repository.caixa;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.caixa.Caixa;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.tickets.TicketGarantia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CaixaRepository extends JpaRepository<Caixa, Long>,
        JpaSpecificationExecutor<Caixa> {
}
