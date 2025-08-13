package br.com.norteautopecas.painel_administrativo_backend.infra.dto;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Status;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StoreInformation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public record TicketGarantiaDetailsDTO(
        Long id,
        @JsonProperty("nome_cliente")

        String nomeCliente,

        String loja,

        String fornecedor,

        @JsonProperty("cpf_cnpj")
        String cpfCnpj,

        @JsonProperty("nota_de_venda")
        String nota,

        String descricao,

        @JsonProperty("usuario_cadastro")
        Long usuarioCadastro,

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
