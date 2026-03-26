package br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ListarCaixaRequestDTO(

        @NotNull(message = "O campo loja é obrigatório")
        @Min(value = 100, message = "A loja deve ter no mínimo 3 dígitos")
        @Max(value = 999, message = "A loja deve ter no máximo 3 dígitos")
        Integer loja,

        @NotNull(message = "A data é obrigatória")
        LocalDate data,

        /**
         * Data final (opcional). Quando informado, a busca é feita por período (dataInicio..dataFim).
         * Requer permissão VISUALIZAR:VALORES_POR_PERIODO.
         */
        @JsonProperty("data_fim")
        LocalDate dataFim

) {
}
