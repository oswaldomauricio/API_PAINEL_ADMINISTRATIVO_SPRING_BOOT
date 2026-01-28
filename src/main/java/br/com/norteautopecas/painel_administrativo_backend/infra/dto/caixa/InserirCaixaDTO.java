package br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InserirCaixaDTO(
        @NotNull(message = "o Campo loja é obrigatório")
        @Min(value = 100, message = "A loja deve ter no mínimo 3 dígitos")
        @Max(value = 999, message = "A loja deve ter no máximo 3 dígitos")
        Integer loja,

        @NotNull(message = "o id do usuario de criação é obrigatório")
        @JsonProperty("id_usuario")
        @Min(value = 1, message = "O ID do usuário deve ser maior que 0")
        Long usuarioId,

        @NotNull(message = "Campo obrigatório!")
        String tipoOperacao,

        @NotNull(message = "Campo obrigatório!")
        LocalDateTime data,

        @NotNull(message = "Campo obrigatório!")
        BigDecimal valor,

        @NotNull(message = "Campo obrigatório!")
        String categoria,

        @NotNull(message = "Campo obrigatório!")
        @Size(max = 10)
        String numeroDocumento,

        @NotNull(message = "Campo obrigatório!")
        @Size(max = 50)
        String origem

        ) {
}
