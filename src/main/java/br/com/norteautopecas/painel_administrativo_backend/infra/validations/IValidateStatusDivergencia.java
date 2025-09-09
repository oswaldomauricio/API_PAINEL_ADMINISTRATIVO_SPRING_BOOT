package br.com.norteautopecas.painel_administrativo_backend.infra.validations;

import br.com.norteautopecas.painel_administrativo_backend.infra.enums.StatusDivergencia;
import org.springframework.stereotype.Component;

@Component
public interface IValidateStatusDivergencia {
    void validar(StatusDivergencia statusAtual, StatusDivergencia novoStatus) throws ValidateException;
}
