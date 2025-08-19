package br.com.norteautopecas.painel_administrativo_backend.controllers;

import br.com.norteautopecas.painel_administrativo_backend.bussines.FileStorageService;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.ListarFilesPorTicketIdDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketDivergenciaDetailsDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.UploadFileResponseDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketFiles;
import br.com.norteautopecas.painel_administrativo_backend.infra.exception.FileNotFoundException;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketFilesRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/v1/file")
@SecurityRequirement(name = "bearer-key")
public class FileStorageController {
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private TicketFilesRepository ticketFilesRepository;

    private static final Logger logger = LoggerFactory.getLogger(FileStorageController.class);

    @PostMapping("/uploadFile/{ticketId}")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file,
                        @PathVariable Long ticketId) {

        UploadFileResponseDTO fileName = fileStorageService.storeFile(file, ticketId);


        return ResponseEntity.ok().body(fileName);

    }

    @PostMapping("/uploadMultipleFiles/{ticketId}")
    public ResponseEntity<List<UploadFileResponseDTO>> uploadMultipleFiles(@RequestParam(
            "files") MultipartFile[] files, @PathVariable Long ticketId) {

        List<UploadFileResponseDTO> responseList = Arrays.stream(files)
                .map(file -> fileStorageService.storeFile(file, ticketId))
                .toList();

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/downloadFile/{ticketId}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long ticketId,
                                                 @PathVariable String fileName,
                                                 HttpServletRequest request) {

        TicketFiles ticketFile = ticketFilesRepository.findByTicketIdAndFileName(ticketId, fileName)
                .orElseThrow(() -> new FileNotFoundException("Arquivo não encontrado ou não pertence ao ticket informado."));

        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @DeleteMapping("/deleteFile/{ticketId}/{fileName:.+}")
    @Transactional
    public ResponseEntity<String> deleteFile(@PathVariable Long ticketId,
                                             @PathVariable String fileName) {
        TicketFiles ticketFile = ticketFilesRepository.findByTicketIdAndFileName(ticketId, fileName)
                .orElseThrow(() -> new FileNotFoundException("Arquivo não encontrado ou não pertence ao ticket informado."));

        boolean deleted = fileStorageService.deleteFile(fileName);

        if (deleted) {
            ticketFilesRepository.delete(ticketFile);
            return ResponseEntity.ok("Arquivo deletado com sucesso: " + fileName);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Arquivo não encontrado ou erro ao deletar: " + fileName);
        }
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Page<ListarFilesPorTicketIdDTO>> consultarTicketsPorLoja(
            @PathVariable Long ticketId,
            @PageableDefault(size = 1000, sort = "ticketId", direction =
                    Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(fileStorageService.listarFilesPorTicket(ticketId, pageable));
    }


}
