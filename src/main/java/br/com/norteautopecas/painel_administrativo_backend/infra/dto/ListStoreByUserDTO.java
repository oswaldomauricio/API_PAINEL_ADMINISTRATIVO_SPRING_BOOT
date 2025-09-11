package br.com.norteautopecas.painel_administrativo_backend.infra.dto;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.User;

public record ListStoreByUserDTO(
        Long idUser,
        String nome,
        Integer loja
) {
}
