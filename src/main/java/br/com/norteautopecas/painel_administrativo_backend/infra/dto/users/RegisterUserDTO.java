package br.com.norteautopecas.painel_administrativo_backend.infra.dto.users;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserDTO(
        @NotBlank(message = "nome de login é obrigatório")
        String login,
        @NotBlank(message = "senha é obrigatória")
        String senha
) {
}
