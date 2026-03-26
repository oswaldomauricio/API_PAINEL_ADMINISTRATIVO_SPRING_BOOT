package br.com.norteautopecas.painel_administrativo_backend.infra.repository.caixa;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.caixa.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface CaixaRepository extends JpaRepository<Caixa, Long>,
        JpaSpecificationExecutor<Caixa> {

    /**
     * Busca registros de uma loja em um dia específico.
     */
    @Query("SELECT c FROM Caixa c WHERE c.loja.loja = :loja AND c.dataInsercao = :data ORDER BY c.dataInsercao DESC, c.createdAt DESC")
    List<Caixa> findByLojaAndData(@Param("loja") Integer loja,
                                   @Param("data") LocalDate data);

    /**
     * Busca registros de uma loja em um intervalo de datas (período).
     */
    @Query("SELECT c FROM Caixa c WHERE c.loja.loja = :loja AND c.dataInsercao BETWEEN :dataInicio AND :dataFim ORDER BY c.dataInsercao DESC, c.createdAt DESC")
    List<Caixa> findByLojaAndPeriodo(@Param("loja") Integer loja,
                                      @Param("dataInicio") LocalDate dataInicio,
                                      @Param("dataFim") LocalDate dataFim);

    // =========================================================================
    // AGREGAÇÕES PARA RESUMO
    // =========================================================================

    /**
     * Soma total de ENTRADAS no período selecionado (dataInicio..dataFim).
     */
    @Query("SELECT COALESCE(SUM(c.valor), 0) FROM Caixa c " +
           "WHERE c.loja.loja = :loja " +
           "AND c.tipoOperacao = br.com.norteautopecas.painel_administrativo_backend.infra.enums.caixa.TipoOperacao.ENTRADA " +
           "AND c.dataInsercao BETWEEN :dataInicio AND :dataFim")
    BigDecimal sumEntradasNoPeriodo(@Param("loja") Integer loja,
                                     @Param("dataInicio") LocalDate dataInicio,
                                     @Param("dataFim") LocalDate dataFim);

    /**
     * Soma total de SAÍDAS no período selecionado (dataInicio..dataFim).
     */
    @Query("SELECT COALESCE(SUM(c.valor), 0) FROM Caixa c " +
           "WHERE c.loja.loja = :loja " +
           "AND c.tipoOperacao = br.com.norteautopecas.painel_administrativo_backend.infra.enums.caixa.TipoOperacao.SAIDA " +
           "AND c.dataInsercao BETWEEN :dataInicio AND :dataFim")
    BigDecimal sumSaidasNoPeriodo(@Param("loja") Integer loja,
                                   @Param("dataInicio") LocalDate dataInicio,
                                   @Param("dataFim") LocalDate dataFim);

    /**
     * Saldo total acumulado desde o início até a dataCorte (inclusive).
     * Faz: SUM(ENTRADA) - SUM(SAÍDA) de todos os registros até a data informada.
     */
    @Query("SELECT COALESCE(SUM(CASE WHEN c.tipoOperacao = " +
           "br.com.norteautopecas.painel_administrativo_backend.infra.enums.caixa.TipoOperacao.ENTRADA THEN c.valor " +
           "WHEN c.tipoOperacao = " +
           "br.com.norteautopecas.painel_administrativo_backend.infra.enums.caixa.TipoOperacao.SAIDA THEN -c.valor " +
           "ELSE 0 END), 0) FROM Caixa c " +
           "WHERE c.loja.loja = :loja " +
           "AND c.dataInsercao <= :dataCorte")
    BigDecimal sumSaldoTotalAteData(@Param("loja") Integer loja,
                                     @Param("dataCorte") LocalDate dataCorte);
}
