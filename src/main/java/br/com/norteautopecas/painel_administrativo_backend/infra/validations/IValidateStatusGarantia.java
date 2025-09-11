package br.com.norteautopecas.painel_administrativo_backend.infra.validations;

import br.com.norteautopecas.painel_administrativo_backend.infra.enums.StatusGarantia;

public interface IValidateStatusGarantia {
    void validar(StatusGarantia statusAtual, StatusGarantia novoStatus) throws ValidateException;

}
