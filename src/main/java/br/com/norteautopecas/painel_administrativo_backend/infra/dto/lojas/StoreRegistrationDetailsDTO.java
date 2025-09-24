package br.com.norteautopecas.painel_administrativo_backend.infra.dto.lojas;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.User;
import com.fasterxml.jackson.annotation.JsonAlias;

public record StoreRegistrationDetailsDTO(
        @JsonAlias("id_usuario")
        Long idUser,
        @JsonAlias("nome_usuario")
        String nomeUsuario,
        Integer loja
) {
    public StoreRegistrationDetailsDTO(Long id, Integer loja, User user) {
        this(
                id,
                user.getLogin(),
                loja
        );
    }
}
