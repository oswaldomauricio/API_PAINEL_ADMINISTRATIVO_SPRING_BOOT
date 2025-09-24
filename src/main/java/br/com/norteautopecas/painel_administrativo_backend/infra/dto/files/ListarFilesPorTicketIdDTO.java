package br.com.norteautopecas.painel_administrativo_backend.infra.dto.files;

public record ListarFilesPorTicketIdDTO(
        Long id,
        String fileName,
        Long fileSize,
        Long ticketId,
        String urlDownload
) {
}
