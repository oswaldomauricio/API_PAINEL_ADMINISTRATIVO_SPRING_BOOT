package br.com.norteautopecas.painel_administrativo_backend.infra.dto.garantia;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.produto.ProdutoCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.enums.StatusGarantia;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record TicketGarantiaDetailsDTO(
        Long id,

        @JsonProperty("nome_cliente")
        String nomeCliente,

        Integer loja,

        String fornecedor,

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

        StatusGarantia status,

        List<ProdutoCreateDTO> produtos
) {
}
