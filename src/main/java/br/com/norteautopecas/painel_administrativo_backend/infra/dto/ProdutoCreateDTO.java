package br.com.norteautopecas.painel_administrativo_backend.infra.dto;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Tipo;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProdutoCreateDTO(
        @JsonProperty("codigo_produto")
        @NotBlank(message = "o Campo código do produto é obrigatório")
        String codigoProduto,

        @JsonProperty("quantidade")
        @NotBlank(message = "o Campo quantidade é obrigatório")
        Integer quantidade,

        @JsonProperty("tipo_divergencia")
        Tipo tipo,

        @JsonProperty("valor_unitario")
        BigDecimal valorUnitario
) {
}
