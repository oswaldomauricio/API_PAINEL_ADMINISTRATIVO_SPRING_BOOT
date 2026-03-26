package br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InserirCaixaDTO(

        @NotNull(message = "O campo loja é obrigatório")
        @Min(value = 100, message = "A loja deve ter no mínimo 3 dígitos")
        @Max(value = 999, message = "A loja deve ter no máximo 3 dígitos")
        Integer loja,

        @NotNull(message = "O tipo de operação é obrigatório")
        @JsonProperty("tipo_operacao")
        String tipoOperacao,

        @NotNull(message = "A data de inserção é obrigatória")
        @JsonProperty("data_insercao")
        LocalDate dataInsercao,

        @NotNull(message = "O valor é obrigatório")
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
        BigDecimal valor,

        @NotNull(message = "O ID da categoria é obrigatório")
        @JsonProperty("id_categoria")
        Long idCategoria,

        @Size(max = 10, message = "O número do documento deve ter no máximo 10 caracteres")
        @JsonProperty("numero_documento")
        String numeroDocumento,

        @Size(max = 50, message = "A origem deve ter no máximo 50 caracteres")
        String origem

) {
}
