package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketMessageCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketStatusHistoricoCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketStatusHistoricoDetailsDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketStatusHistoricoListByIdDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.enums.StatusDivergencia;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketStatusHistoricoDivergencia;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketDivergenciaRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketStatusHistoricoDivergenciaRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.UsersRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.IValidateStatusDivergencia;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketStatusHistoricoDivergenciaService {

    @Autowired
    private TicketStatusHistoricoDivergenciaRepository historicoRepository;

    @Autowired
    private TicketDivergenciaRepository ticketDivergenciaRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private List<IValidateStatusDivergencia> validadores;

    @Autowired
    private TicketMessageService ticketMessageService;

    public TicketStatusHistoricoDetailsDTO atualizarStatusDeTicket(TicketStatusHistoricoCreateDTO dados) {
        var usuario = usersRepository.findById(dados.idUser())
                .orElseThrow(() -> new ValidateException("Usuário não encontrado"));

        var ticketDivergencia = ticketDivergenciaRepository.findById(dados.ticketId())
                .orElseThrow(() -> new ValidateException("Ticket de " +
                        "divergência não encontrado!"));

        StatusDivergencia novoStatus;

        try {
            novoStatus = StatusDivergencia.valueOf(dados.status().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidateException("O status '" + dados.status() + "' é inválido.");
        }

        final StatusDivergencia statusAtual = ticketDivergencia.getStatus();

        validadores.forEach(v -> v.validar(
                statusAtual,
                novoStatus
        ));


        TicketStatusHistoricoDivergencia historico =
                new TicketStatusHistoricoDivergencia(
                        ticketDivergencia,
                        novoStatus,
                        dados.mensagem(),
                        usuario
                );

        historicoRepository.save(historico);

        ticketDivergencia.setStatus(historico.getStatus());
        ticketDivergenciaRepository.save(ticketDivergencia);

        //enviar atualização de status nas mensagens.
        String templateMensagem = """
                Novo status: :status
                Mensagem: :mensagem
                Data Atualização: :data
                """;

        String mensagemFinal = templateMensagem
                .replace(":status", dados.status())
                .replace(":mensagem", historico.getMensagem())
                .replace(":data", historico.getDataAtualizacao()
                        .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        var dadosAdicionarMensagem = new TicketMessageCreateDTO(
                ticketDivergencia.getId(),
                usuario.getId(),
                mensagemFinal,
                false
        );

        ticketMessageService.adicionarMensagemGarantia(dadosAdicionarMensagem);

        return new TicketStatusHistoricoDetailsDTO(
                historico.getTicketDivergencia().getId(),
                historico.getStatus().toString(),
                historico.getMensagem(),
                historico.getUsuario().getLogin(),
                historico.getDataAtualizacao().toString()
        );
    }

    public List<TicketStatusHistoricoDetailsDTO> listarStatusDeTicket(TicketStatusHistoricoListByIdDTO dados) {
        List<TicketStatusHistoricoDivergencia> historicos;

        var ticketDivergencia =
                ticketDivergenciaRepository.findById(dados.ticketId())
                        .orElseThrow(() -> new ValidateException("Ticket de " +
                                "divergência" +
                                " não encontrado"));

        historicos =
                historicoRepository.findByTicketDivergenciaIdOrderByDataAtualizacaoDesc(ticketDivergencia.getId());


        return historicos.stream()
                .map(h -> new TicketStatusHistoricoDetailsDTO(
                        h.getTicketDivergencia().getId(),
                        h.getStatus().getDescricao(),
                        h.getMensagem(),
                        h.getUsuario().getLogin(),
                        h.getDataAtualizacao().toString()
                ))
                .collect(Collectors.toList());
    }
}
