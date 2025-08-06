package br.com.norteautopecas.painel_administrativo_backend.infra.repository.user;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    boolean existsByIdAndLoja(Long id, Integer loja);
}
