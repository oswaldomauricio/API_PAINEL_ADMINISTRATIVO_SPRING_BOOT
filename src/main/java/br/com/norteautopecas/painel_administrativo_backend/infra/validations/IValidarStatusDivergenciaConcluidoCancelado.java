package br.com.norteautopecas.painel_administrativo_backend.infra.validations;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StatusDivergencia;
import org.springframework.stereotype.Component;

@Component
public class IValidarStatusDivergenciaConcluidoCancelado implements IValidateStatusDivergencia {
    @Override
    public void validar(StatusDivergencia statusAtual, StatusDivergencia novoStatus) throws ValidateException {
        if (statusAtual == StatusDivergencia.CANCELADO || statusAtual == StatusDivergencia.CONCLUIDO) {
            throw new ValidateException("Não é permitido alterar o status de " +
                    "um ticket CANCELADO ou CONCLUIDO.");
        }
    }
}
