package br.com.norteautopecas.painel_administrativo_backend.infra.validations;

import br.com.norteautopecas.painel_administrativo_backend.infra.enums.StatusGarantia;
import org.springframework.stereotype.Component;

@Component
public class IValidarStatusGarantiaConcluidoCancelado implements IValidateStatusGarantia {

    @Override
    public void validar(StatusGarantia statusAtual, StatusGarantia novoStatus) throws ValidateException {
        if (statusAtual == StatusGarantia.CANCELADO || statusAtual == StatusGarantia.CONCLUIDO) {
            throw new ValidateException("Não é permitido alterar o status de " +
                    "um ticket CANCELADO ou CONCLUIDO.");
        }
    }
}
