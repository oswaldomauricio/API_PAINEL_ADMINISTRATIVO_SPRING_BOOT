package br.com.norteautopecas.painel_administrativo_backend.infra.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @NotBlank
        String login,
        @NotBlank
        String senha
) {
}
