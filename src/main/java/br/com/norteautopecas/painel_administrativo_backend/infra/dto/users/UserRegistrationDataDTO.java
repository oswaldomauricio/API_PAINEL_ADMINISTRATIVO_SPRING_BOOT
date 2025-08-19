package br.com.norteautopecas.painel_administrativo_backend.infra.dto.users;

public record UserRegistrationDataDTO(
        Long id,
        String login,
        String role
) {
}
