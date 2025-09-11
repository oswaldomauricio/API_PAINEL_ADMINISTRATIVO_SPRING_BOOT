package br.com.norteautopecas.painel_administrativo_backend.infra.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record ListStoreInformationDTO(
        Integer loja,
        String nomeLoja,
        String endereco,
        String estado,
        String sigla,
        String cidade,
        String email,
        Double latitude,
        Double longitude,
        String segmentacao,
        String segmentacao2,
        String telefone,
        String whatsapp,
        String horario
) {
}
