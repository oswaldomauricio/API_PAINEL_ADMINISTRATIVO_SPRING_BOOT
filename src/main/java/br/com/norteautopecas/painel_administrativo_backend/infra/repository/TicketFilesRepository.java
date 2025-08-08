package br.com.norteautopecas.painel_administrativo_backend.infra.repository;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketFiles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketFilesRepository extends JpaRepository<TicketFiles, Long> {

    Optional<TicketFiles> findByTicketIdAndFileName(Long ticketId, String fileName);

    boolean existsByTicketIdAndFileName(Long ticketId, String fileName);
    boolean existsByFileName(String fileName);
}
