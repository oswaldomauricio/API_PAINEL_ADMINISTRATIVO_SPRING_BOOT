package br.com.norteautopecas.painel_administrativo_backend.infra.dto.users;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Roles;

public record UserRegistrationDataDTO(
        Long id,
        String login,
        Roles role
) {
}
