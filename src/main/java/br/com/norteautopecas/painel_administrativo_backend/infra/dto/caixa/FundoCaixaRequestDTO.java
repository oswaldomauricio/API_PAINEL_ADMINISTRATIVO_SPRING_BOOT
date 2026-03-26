package br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record FundoCaixaRequestDTO(

        @NotNull(message = "O campo loja é obrigatório")
        @Min(value = 100, message = "A loja deve ter no mínimo 3 dígitos")
        @Max(value = 999, message = "A loja deve ter no máximo 3 dígitos")
        Integer loja,

        @NotNull(message = "O valor do fundo de caixa é obrigatório")
        @DecimalMin(value = "0.00", message = "O valor do fundo de caixa não pode ser negativo")
        @JsonProperty("valor_fundo")
        BigDecimal valorFundo

) {
}
