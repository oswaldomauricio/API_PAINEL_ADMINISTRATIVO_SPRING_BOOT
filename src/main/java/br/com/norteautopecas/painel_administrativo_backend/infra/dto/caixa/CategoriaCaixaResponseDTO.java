package br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.caixa.CategoriaCaixa;

import java.time.LocalDateTime;

public record CategoriaCaixaResponseDTO(
        Long id,
        String nome,
        Boolean ativo,
        LocalDateTime createdAt
) {
    public static CategoriaCaixaResponseDTO fromEntity(CategoriaCaixa categoria) {
        return new CategoriaCaixaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getAtivo(),
                categoria.getCreatedAt()
        );
    }
}
