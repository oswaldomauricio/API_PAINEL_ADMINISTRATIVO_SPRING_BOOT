package br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.caixa.FundoCaixa;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record FundoCaixaResponseDTO(

        Long id,

        Integer loja,

        @JsonProperty("nome_loja")
        String nomeLoja,

        @JsonProperty("valor_fundo")
        BigDecimal valorFundo

) {
    public static FundoCaixaResponseDTO fromEntity(FundoCaixa fundo) {
        return new FundoCaixaResponseDTO(
                fundo.getId(),
                fundo.getLoja().getLoja(),
                fundo.getLoja().getNomeLoja(),
                fundo.getValorFundo()
        );
    }
}
