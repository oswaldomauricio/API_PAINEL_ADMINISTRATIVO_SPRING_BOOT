package br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResumoCaixaResponseDTO(

        Integer loja,

        /**
         * Data inicial do período consultado.
         */
        @JsonProperty("data_inicio")
        LocalDate dataInicio,

        /**
         * Data final do período consultado (igual à dataInicio quando buscado por dia).
         */
        @JsonProperty("data_fim")
        LocalDate dataFim,

        /**
         * Valor informativo do fundo de caixa da loja. Não impacta nenhum cálculo.
         * Pode ser null se o fundo ainda não foi cadastrado para a loja.
         */
        @JsonProperty("fundo_de_caixa")
        BigDecimal fundoDeCaixa,

        /**
         * Soma de todos os lançamentos de ENTRADA no período selecionado.
         */
        @JsonProperty("valor_entrada")
        BigDecimal valorEntrada,

        /**
         * Soma de todos os lançamentos de SAÍDA no período selecionado.
         */
        @JsonProperty("valor_saida")
        BigDecimal valorSaida,

        /**
         * Saldo total acumulado da loja desde o primeiro lançamento até a data final do período.
         * Calculado como: SUM(ENTRADAS) - SUM(SAÍDAS) de todos os registros históricos até essa data.
         */
        @JsonProperty("saldo_total")
        BigDecimal saldoTotal

) {
}
