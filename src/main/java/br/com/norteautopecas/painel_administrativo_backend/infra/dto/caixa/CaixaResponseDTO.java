package br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.caixa.Caixa;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CaixaResponseDTO(

        Long id,

        @JsonProperty("data_insercao")
        LocalDate dataInsercao,

        @JsonProperty("numero_documento")
        String numeroDocumento,

        @JsonProperty("tipo_operacao")
        String tipoOperacao,

        String categoria,

        BigDecimal valor,

        String origem

) {
    public static CaixaResponseDTO fromEntity(Caixa caixa) {
        return new CaixaResponseDTO(
                caixa.getId(),
                caixa.getDataInsercao(),
                caixa.getNumeroDocumento(),
                caixa.getTipoOperacao().getTipoOperacao(),
                caixa.getCategoria().getNome(),
                caixa.getValor(),
                caixa.getOrigem()
        );
    }
}
