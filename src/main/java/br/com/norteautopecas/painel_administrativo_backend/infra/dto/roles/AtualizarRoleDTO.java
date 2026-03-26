package br.com.norteautopecas.painel_administrativo_backend.infra.dto.roles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AtualizarRoleDTO(

        @NotBlank(message = "O nome da role é obrigatório")
        @Size(max = 100, message = "O nome da role deve ter no máximo 100 caracteres")
        String name

) {
}
