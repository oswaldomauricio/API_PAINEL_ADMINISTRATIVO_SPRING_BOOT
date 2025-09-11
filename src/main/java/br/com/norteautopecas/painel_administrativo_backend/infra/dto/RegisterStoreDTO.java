package br.com.norteautopecas.painel_administrativo_backend.infra.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterStoreDTO(
        @NotNull(message = "o Campo loja é obrigatório")
        @Min(value = 100, message = "A loja deve ter no mínimo 3 dígitos")
        @Max(value = 999, message = "A loja deve ter no máximo 3 dígitos")
        Integer loja,
        @NotNull(message = "o id do usuario é obrigatório")
        @JsonAlias("id_usuario")
        Long idUser
) {
}
