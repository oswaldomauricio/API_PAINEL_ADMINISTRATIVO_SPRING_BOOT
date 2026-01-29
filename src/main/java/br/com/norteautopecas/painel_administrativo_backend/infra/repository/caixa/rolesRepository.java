package br.com.norteautopecas.painel_administrativo_backend.infra.repository.caixa;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface rolesRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findById(Integer id);
}
