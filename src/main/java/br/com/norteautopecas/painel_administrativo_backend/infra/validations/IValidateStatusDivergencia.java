package br.com.norteautopecas.painel_administrativo_backend.infra.validations;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StatusDivergencia;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StatusGarantia;
import org.springframework.stereotype.Component;

@Component
public interface IValidateStatusDivergencia {
    void validar(StatusDivergencia statusAtual, StatusDivergencia novoStatus) throws ValidateException;
}
