package br.com.norteautopecas.painel_administrativo_backend.infra.security;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Roles;

public record TokenDTO(
        String token,
        String nome,
        Roles role,
        Long id
) {
}
