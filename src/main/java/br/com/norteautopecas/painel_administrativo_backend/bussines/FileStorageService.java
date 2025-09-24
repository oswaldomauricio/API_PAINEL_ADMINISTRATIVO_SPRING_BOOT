package br.com.norteautopecas.painel_administrativo_backend.bussines;

import java.nio.file.*;

import br.com.norteautopecas.painel_administrativo_backend.config.FileStorageConfig;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.files.ListarFilesPorTicketIdDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.files.UploadFileResponseDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketFiles;
import br.com.norteautopecas.painel_administrativo_backend.infra.exception.FileNotFoundException;
import br.com.norteautopecas.painel_administrativo_backend.infra.exception.FileStorageException;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketFilesRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Path;

@Service
public class FileStorageService {

    @Autowired
    private TicketFilesRepository ticketFilesRepository;

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException("Não foi possivel criar o " +
                    "diretorio em que os arquivos são armazenados!", e);
        }

    }

    public UploadFileResponseDTO storeFile(MultipartFile file, Long ticketId) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (fileName.contains("..")) {
            throw new FileStorageException("Desculpe, o nome do arquivo contém caracteres inválidos: " + fileName);
        }

        if (fileName.isEmpty()) {
            throw new FileStorageException("Desculpe, o nome do arquivo não pode ser vazio.");
        }

        if (ticketFilesRepository.existsByTicketIdAndFileName(ticketId, fileName)) {
            throw new FileStorageException("Desculpe, já existe um arquivo com o nome: "
                    + fileName + " para o ticket de id: " + ticketId);
        }

        if (ticketFilesRepository.existsByFileName(fileName)) {
            throw new FileStorageException("Desculpe, já existe um arquivo com o nome: "
                    + fileName + " altere o nome do arquivo e tente " +
                    "novamente!");
        }

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation,
                    StandardCopyOption.REPLACE_EXISTING);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/v1/file/downloadFile/")
                    .path(ticketId + "/")
                    .path(fileName)
                    .toUriString();

            TicketFiles ticketFile = new TicketFiles(ticketId, fileName, file.getSize(), fileDownloadUri);
            ticketFilesRepository.save(ticketFile);


            return new UploadFileResponseDTO(fileName, fileDownloadUri,
                    file.getContentType(), file.getSize());

        } catch (Exception e) {
            throw new FileStorageException("Não foi possível armazenar o " +
                    "arquivo " + fileName + "por favor, tente novamente!"
                    , e);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("Arquivo não encontrado" + fileName);
            }


        } catch (Exception e) {
            throw new FileNotFoundException("Arquivo não encontrado" + fileName, e);
        }
    }

    public boolean deleteFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            return Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new FileStorageException("Erro ao deletar o arquivo: " + fileName, e);
        }
    }

    public Page<ListarFilesPorTicketIdDTO> listarFilesPorTicket(Long ticketId,
                                                                Pageable pageable) {
        Page<TicketFiles> ticketFiles = ticketFilesRepository.findByTicketId(ticketId, pageable);

        if (ticketId == null || ticketId <= 0) {
            throw new ValidateException("Ticket não informado, favor, informe" +
                    " um ticket valido.");
        }

        if (ticketFiles.isEmpty()) {
            if (ticketFiles.isEmpty()) {
                return Page.empty(pageable);
            }
        }

        return ticketFiles.map(tf -> new ListarFilesPorTicketIdDTO(
                tf.getId(),
                tf.getFileName(),
                tf.getFileSize(),
                tf.getTicketId(),
                tf.getUrl()
        ));
    }


}
