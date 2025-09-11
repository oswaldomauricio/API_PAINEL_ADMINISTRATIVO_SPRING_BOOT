package br.com.norteautopecas.painel_administrativo_backend.infra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record TicketDivergenciaDetailsDTO(
        Long id,

        Integer loja,

        String fornecedor,

        String cpfCnpj,

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

        Object status,

        List<ProdutoCreateDTO> produtos
) {
}
