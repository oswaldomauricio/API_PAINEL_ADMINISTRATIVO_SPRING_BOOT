package br.com.norteautopecas.painel_administrativo_backend.infra.repository.caixa;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.caixa.CategoriaCaixa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaCaixaRepository extends JpaRepository<CategoriaCaixa, Long> {

    Optional<CategoriaCaixa> findByNomeIgnoreCase(String nome);

    List<CategoriaCaixa> findAllByAtivoTrue();

    boolean existsByNomeIgnoreCase(String nome);
}
