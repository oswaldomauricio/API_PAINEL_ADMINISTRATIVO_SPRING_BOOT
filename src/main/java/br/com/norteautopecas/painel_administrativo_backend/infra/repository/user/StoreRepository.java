package br.com.norteautopecas.painel_administrativo_backend.infra.repository.user;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Store;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    int countByUserIdAndLoja(Long userId, Integer loja);

    List<Store> findAllByUserId(Long userId);

}
