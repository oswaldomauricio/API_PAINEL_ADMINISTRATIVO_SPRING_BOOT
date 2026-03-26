package br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarCategoriaCaixaDTO(

        @NotBlank(message = "O nome da categoria é obrigatório")
        @Size(max = 100, message = "O nome da categoria deve ter no máximo 100 caracteres")
        String nome

) {
}
