package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Status;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.ValidateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TicketStatusHistoricoServiceTest {

    private TicketStatusHistoricoService service;

    @BeforeEach
    void setup() {
        service = new TicketStatusHistoricoService();
    }

    @ParameterizedTest
    @EnumSource(
            value = Status.class,
            names = {"CANCELADO", "RESOLVIDO", "APROVADO", "CONCLUIDO"}
    )
    @DisplayName("Deve lançar exceção ao tentar alterar um ticket com status terminal")
    void naoDevePermitirAlterarStatusTerminal(Status statusTerminal) {
        assertThatThrownBy(() ->
                service.validarSePodeAlterar(statusTerminal, Status.ANALISE)
        )
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Não é permitido alterar o status de um ticket");
    }

    @ParameterizedTest
    @EnumSource(
            value = Status.class,
            names = {"ANALISE", "RECEBIDO"}
    )
    @DisplayName("Deve lançar exceção ao tentar retornar um ticket para o status NOVO")
    void naoDevePermitirRetornarParaNovo(Status statusAtual) {
        assertThatThrownBy(() ->
                service.validarSePodeAlterar(statusAtual, Status.NOVO)
        )
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("retornar o status de um ticket para NOVO");
    }

    @ParameterizedTest
    @CsvSource({
            "NOVO, ANALISE",
            "NOVO, RECEBIDO",
            "NOVO, APROVADO",
            "ANALISE, RECEBIDO",
            "ANALISE, CONCLUIDO",
            "RECEBIDO, ANALISE"
    })
    @DisplayName("Não deve lançar exceção para transições de status permitidas")
    void devePermitirTransicoesValidas(Status statusAtual, Status novoStatus) {
        assertThatCode(() ->
                service.validarSePodeAlterar(statusAtual, novoStatus)
        ).doesNotThrowAnyException();
    }
}