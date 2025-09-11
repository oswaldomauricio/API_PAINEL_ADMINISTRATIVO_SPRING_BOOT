package br.com.norteautopecas.painel_administrativo_backend.infra.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.util.List;

public record TicketGarantiaCreateDTO(
        @JsonProperty("nome_cliente")
        @NotBlank(message = "o Campo nome do cliente é obrigatório")
        String nomeCliente,

        @NotNull(message = "o Campo loja é obrigatório")
        @Min(value = 100, message = "A loja deve ter no mínimo 3 dígitos")
        @Max(value = 999, message = "A loja deve ter no máximo 3 dígitos")
        Integer loja,

        @NotBlank(message = "o Campo fornecedor é obrigatório")
        String fornecedor,

        @JsonProperty("cpf_cnpj")
        @Pattern(regexp = "^[0-9]{11}$|^[0-9]{14}$")
        @NotBlank(message = "o Campo CPF/CNPJ é obrigatório")
        @Size(min = 11, max = 14, message = "O CPF deve ter 11 dígitos e o CNPJ deve ter 14 dígitos")
        String cpfCnpj,

        @NotBlank(message = "o Campo nota é obrigatório")
        @Size(min = 1, max = 9, message = "A nota deve ter entre 1 e 9 caracteres")
        String nota,

        String descricao,

        @NotNull(message = "o id do usuario de criação é obrigatório")
        @JsonProperty("id_usuario")
        @Min(value = 1, message = "O ID do usuário deve ser maior que 0")
        Long usuarioId,

        @NotEmpty(message = "Não é possível criar um ticket sem produtos")
        List<ProdutoCreateDTO> produtos
) {

}