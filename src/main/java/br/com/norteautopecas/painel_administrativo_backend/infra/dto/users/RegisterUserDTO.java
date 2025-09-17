package br.com.norteautopecas.painel_administrativo_backend.infra.dto.users;

import br.com.norteautopecas.painel_administrativo_backend.infra.enums.Roles;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserDTO(
        @NotBlank(message = "nome de login é obrigatório")
        String login,

        @NotBlank(message = "senha é obrigatória")
        String senha,

        @NotBlank(message = "email é obrigatório")
        String email,

        Roles role
) {
}
