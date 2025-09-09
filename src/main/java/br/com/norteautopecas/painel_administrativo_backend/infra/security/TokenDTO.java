package br.com.norteautopecas.painel_administrativo_backend.infra.security;

import br.com.norteautopecas.painel_administrativo_backend.infra.enums.Roles;

public record TokenDTO(
        String token,
        String nome,
        Roles role,
        Long id
) {
}
