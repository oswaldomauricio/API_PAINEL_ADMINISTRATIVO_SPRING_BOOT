package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketMessageCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketStatusHistoricoCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketStatusHistoricoDetailsDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketStatusHistoricoListByIdDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.enums.StatusGarantia;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketStatusHistoricoGarantia;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketGarantiaRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketStatusHistoricoGarantiaRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.UsersRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.IValidateStatusGarantia;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketStatusHistoricoGarantiaService {

    @Autowired
    private TicketStatusHistoricoGarantiaRepository historicoRepository;

    @Autowired
    private TicketGarantiaRepository ticketGarantiaRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private List<IValidateStatusGarantia> validadores;

    @Autowired
    private TicketMessageService ticketMessageService;

    public TicketStatusHistoricoDetailsDTO atualizarStatusDeTicket(TicketStatusHistoricoCreateDTO dados) {
        var usuario = usersRepository.findById(dados.idUser())
                .orElseThrow(() -> new ValidateException("Usuário não encontrado"));

        var ticketGarantia = ticketGarantiaRepository.findById(dados.ticketId())
                .orElseThrow(() -> new ValidateException("Ticket de " +
                        "garantia não encontrado!"));

        StatusGarantia novoStatus;

        try {
            novoStatus = StatusGarantia.valueOf(dados.status().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidateException("O status '" + dados.status() + "' é inválido.");
        }

        final StatusGarantia statusAtual = ticketGarantia.getStatus();

        validadores.forEach(v -> v.validar(
                statusAtual,
                novoStatus
        ));

        TicketStatusHistoricoGarantia historico = new TicketStatusHistoricoGarantia(
                ticketGarantia,
                novoStatus,
                dados.mensagem(),
                usuario
        );

        historicoRepository.save(historico);

        ticketGarantia.setStatus(historico.getStatus());
        ticketGarantiaRepository.save(ticketGarantia);


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
                ticketGarantia.getId(),
                usuario.getId(),
                mensagemFinal,
                false
        );


        ticketMessageService.adicionarMensagemGarantia(dadosAdicionarMensagem);

        return new TicketStatusHistoricoDetailsDTO(
                historico.getTicketGarantia().getId(),
                historico.getStatus().toString(),
                historico.getMensagem(),
                historico.getUsuario().getLogin(),
                historico.getDataAtualizacao().toString()
        );
    }

    public List<TicketStatusHistoricoDetailsDTO> listarStatusDeTicket(TicketStatusHistoricoListByIdDTO dados) {
        List<TicketStatusHistoricoGarantia> historicos;

        var ticketGarantia = ticketGarantiaRepository.findById(dados.ticketId())
                .orElseThrow(() -> new ValidateException("Ticket de garantia não encontrado"));

        historicos =
                historicoRepository.findByTicketGarantiaIdOrderByDataAtualizacaoDesc(ticketGarantia.getId());


        return historicos.stream()
                .map(h -> new TicketStatusHistoricoDetailsDTO(
                        h.getTicketGarantia().getId(),
                        h.getStatus().getDescricao(),
                        h.getMensagem(),
                        h.getUsuario().getLogin(),
                        h.getDataAtualizacao().toString()
                ))
                .collect(Collectors.toList());
    }
}
