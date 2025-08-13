package br.com.norteautopecas.painel_administrativo_backend.infra.dto;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record TicketDivergenciaDetailsDTO(
        Long id,
        @JsonProperty("nome_cliente")

        Integer loja,

        String fornecedor,

        @JsonProperty("cpf_cnpj")
        String cpfCnpj,

        @JsonProperty("nota_de_venda")
        String nota,

        String descricao,

        @JsonProperty("usuario_cadastro")
        String usuarioCadastro,

        @JsonProperty("data_solicitacao")
        LocalDateTime dataSolicitacao,

        @JsonProperty("data_atualizacao")
        LocalDateTime dataAtualizacao,

        @JsonProperty("dias_em_aberto")
        Long diasEmAberto,

        Status status,

        List<ProdutoCreateDTO> produtos
) {
}
