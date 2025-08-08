package br.com.norteautopecas.painel_administrativo_backend.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FileStorageException extends RuntimeException{
    private static final Long serialVersionUID = 1L;

    public FileStorageException(String exception) {
        super(exception);
    }

    public FileStorageException(String exception, Throwable cause) {
        super(exception, cause);
    }
}
