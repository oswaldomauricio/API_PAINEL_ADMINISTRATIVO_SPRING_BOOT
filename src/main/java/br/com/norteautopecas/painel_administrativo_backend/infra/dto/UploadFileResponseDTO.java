package br.com.norteautopecas.painel_administrativo_backend.infra.dto;

public record UploadFileResponseDTO(
        String fileName,
        String fileDownloadUri,
        String fileType,
        Long size
) {
    private static final long serialVersionUID = 1L;

    public UploadFileResponseDTO(String fileName, String fileDownloadUri, String fileType, Long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
}
