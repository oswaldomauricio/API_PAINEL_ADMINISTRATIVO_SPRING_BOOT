package br.com.norteautopecas.painel_administrativo_backend.infra.security;

public record TokenDTO(
        String token,
        String nome,
        String role,
        Long id
) {
}
