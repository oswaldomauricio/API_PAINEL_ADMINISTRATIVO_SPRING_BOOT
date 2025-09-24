package br.com.norteautopecas.painel_administrativo_backend.infra.dto.lojas;

public record ListStoreByUserDTO(
        Long idUser,
        String nome,
        Integer loja
) {
}
