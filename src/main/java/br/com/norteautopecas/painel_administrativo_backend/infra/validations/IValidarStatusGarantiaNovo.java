package br.com.norteautopecas.painel_administrativo_backend.infra.validations;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StatusGarantia;
import org.springframework.stereotype.Component;

@Component
public class IValidarStatusGarantiaNovo implements IValidateStatusGarantia {

    @Override
    public void validar(StatusGarantia statusAtual, StatusGarantia novoStatus) throws ValidateException {
        if (statusAtual != StatusGarantia.NOVO && novoStatus == StatusGarantia.NOVO) {
            throw new ValidateException("Não é permitido retornar o status de um ticket para NOVO.");
        }
    }
}
