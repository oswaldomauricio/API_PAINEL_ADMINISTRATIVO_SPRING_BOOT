package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketStatusHistoricoCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketStatusHistoricoDetailsDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketStatusHistoricoListByIdDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Status;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketStatusHistorico;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketDivergenciaRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketGarantiaRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketStatusHistoricoRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.UsersRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketStatusHistoricoService {

    @Autowired
    private TicketStatusHistoricoRepository historicoRepository;

    @Autowired
    private TicketDivergenciaRepository ticketDivergenciaRepository;

    @Autowired
    private TicketGarantiaRepository ticketGarantiaRepository;

    @Autowired
    private UsersRepository usersRepository;

    public TicketStatusHistoricoDetailsDTO atualizarStatusDeTicket(TicketStatusHistoricoCreateDTO dados) {
        var usuario = usersRepository.findById(dados.idUser())
                .orElseThrow(() -> new ValidateException("Usuário não encontrado"));

        if ("GARANTIA".equalsIgnoreCase(dados.ticketTipo())) {
            var ticketGarantia = ticketGarantiaRepository.findById(dados.ticketId())
                    .orElseThrow(() -> new ValidateException("Ticket de garantia não encontrado"));

            validarSePodeAlterar(ticketGarantia.getTicket().getStatus(),
                    dados.status());

            TicketStatusHistorico historico = new TicketStatusHistorico(
                    null,
                    ticketGarantia,
                    dados.status(),
                    dados.mensagem(),
                    usuario
            );

            historicoRepository.save(historico);

            ticketGarantia.getTicket().setStatus(historico.getStatus());
            ticketGarantiaRepository.save(ticketGarantia);

            return new TicketStatusHistoricoDetailsDTO(
                    historico.getTicketGarantia().getId(),
                    "GARANTIA",
                    historico.getStatus().toString(),
                    historico.getMensagem(),
                    historico.getUsuario().getLogin(),
                    historico.getDataAtualizacao().toString()
            );


        } else if ("DIVERGENCIA".equalsIgnoreCase(dados.ticketTipo())) {
            var ticketDivergencia = ticketDivergenciaRepository.findById(dados.ticketId())
                    .orElseThrow(() -> new ValidateException("Ticket de divergência não encontrado"));

            validarSePodeAlterar(ticketDivergencia.getTicket().getStatus(),
                    dados.status());


            TicketStatusHistorico historico = new TicketStatusHistorico(
                    ticketDivergencia,
                    null,
                    dados.status(),
                    dados.mensagem(),
                    usuario
            );

            historicoRepository.save(historico);

            ticketDivergencia.getTicket().setStatus(historico.getStatus());
            ticketDivergenciaRepository.save(ticketDivergencia);

            return new TicketStatusHistoricoDetailsDTO(
                    historico.getTicketDivergencia().getId(),
                    "DIVERGENCIA",
                    historico.getStatus().toString(),
                    historico.getMensagem(),
                    historico.getUsuario().getLogin(),
                    historico.getDataAtualizacao().toString()
            );

        } else {
            throw new ValidateException("Tipo de ticket inválido" + dados.ticketTipo());
        }
    }

    public List<TicketStatusHistoricoDetailsDTO> listarStatusDeTicket(TicketStatusHistoricoListByIdDTO dados) {
        List<TicketStatusHistorico> historicos;

        if (dados.ticketTipo().equalsIgnoreCase("GARANTIA")) {
            var ticketGarantia = ticketGarantiaRepository.findById(dados.ticketId())
                    .orElseThrow(() -> new ValidateException("Ticket de garantia não encontrado"));

            historicos = historicoRepository.findByTicketGarantia(ticketGarantia);

        } else if (dados.ticketTipo().equalsIgnoreCase("DIVERGENCIA")) {
            var ticketDivergencia =
                    ticketDivergenciaRepository.findById(dados.ticketId())
                            .orElseThrow(() -> new ValidateException("Ticket de divergência não encontrado"));

            historicos = historicoRepository.findByTicketDivergencia(ticketDivergencia);

        } else {
            throw new ValidateException("Tipo de ticket inválido" + dados.ticketTipo());
        }

        return historicos.stream()
                .map(h -> new TicketStatusHistoricoDetailsDTO(
                        h.getTicketGarantia() != null ? h.getTicketGarantia().getId() : h.getTicketDivergencia().getId(),
                        dados.ticketTipo().toUpperCase(),
                        h.getStatus().toString(),
                        h.getMensagem(),
                        h.getUsuario().getLogin(),
                        h.getDataAtualizacao().toString()
                ))
                .collect(Collectors.toList());
    }

    public void validarSePodeAlterar(Status statusAtual, Status novoStatus) {
        if (statusAtual == Status.CANCELADO || statusAtual == Status.RESOLVIDO || statusAtual == Status.APROVADO || statusAtual == Status.CONCLUIDO) {
            throw new ValidateException("Não é permitido alterar o status de " +
                    "um ticket CANCELADO, RESOLVIDO, APROVADO ou CONCLUIDO.");
        }

        if (statusAtual != Status.NOVO && novoStatus == Status.NOVO) {
            throw new ValidateException("Não é permitido retornar o status de um ticket para NOVO.");
        }
    }
}
