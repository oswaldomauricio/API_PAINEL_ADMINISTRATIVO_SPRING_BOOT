package br.com.norteautopecas.painel_administrativo_backend.infra.validations;

public class ValidateException extends RuntimeException {
    public ValidateException(String message) {
        super(message);
    }
}
