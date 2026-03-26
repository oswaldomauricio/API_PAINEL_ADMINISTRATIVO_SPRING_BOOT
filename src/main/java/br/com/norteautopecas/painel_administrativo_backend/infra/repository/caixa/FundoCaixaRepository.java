package br.com.norteautopecas.painel_administrativo_backend.infra.repository.caixa;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.caixa.FundoCaixa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FundoCaixaRepository extends JpaRepository<FundoCaixa, Long> {

    Optional<FundoCaixa> findByLojaLoja(Integer loja);

    boolean existsByLojaLoja(Integer loja);
}
