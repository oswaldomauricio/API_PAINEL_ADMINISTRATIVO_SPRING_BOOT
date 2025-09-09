package br.com.norteautopecas.painel_administrativo_backend.infra.validations;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StatusGarantia;

public interface IValidateStatusGarantia {
    void validar(StatusGarantia statusAtual, StatusGarantia novoStatus) throws ValidateException;

}
