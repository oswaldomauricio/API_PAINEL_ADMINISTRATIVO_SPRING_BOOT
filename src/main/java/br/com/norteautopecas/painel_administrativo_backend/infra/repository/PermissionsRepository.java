package br.com.norteautopecas.painel_administrativo_backend.infra.repository;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionsRepository extends JpaRepository<Permissions, Long> {

    Optional<Permissions> findByPermissionIgnoreCase(String permission);

    boolean existsByPermissionIgnoreCase(String permission);
}
