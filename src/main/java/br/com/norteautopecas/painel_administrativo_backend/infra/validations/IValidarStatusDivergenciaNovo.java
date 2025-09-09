package br.com.norteautopecas.painel_administrativo_backend.infra.validations;


import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StatusDivergencia;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StatusGarantia;
import org.springframework.stereotype.Component;

@Component
public class IValidarStatusDivergenciaNovo implements IValidateStatusDivergencia {
    @Override
    public void validar(StatusDivergencia statusAtual, StatusDivergencia novoStatus) throws ValidateException {
        if (statusAtual != StatusDivergencia.NOVO && novoStatus == StatusDivergencia.NOVO) {
            throw new ValidateException("Não é permitido retornar o status de um ticket para NOVO.");
        }
    }

}
