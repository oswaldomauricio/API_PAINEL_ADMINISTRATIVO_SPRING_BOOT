package br.com.norteautopecas.painel_administrativo_backend.bussines;

import java.nio.file.*;

import br.com.norteautopecas.painel_administrativo_backend.config.FileStorageConfig;
import br.com.norteautopecas.painel_administrativo_backend.infra.exception.FileNotFoundException;
import br.com.norteautopecas.painel_administrativo_backend.infra.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Service
public class FileStorageService {

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

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException(
                        "Desculpe, o nome do arquivo contém caracteres " +
                                "inválidos: " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation,
                    StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (Exception e) {
            throw new FileStorageException("Não foi possível armazenar o arquivo"
                    + fileName + "por favor, tente novamente!"
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


}
