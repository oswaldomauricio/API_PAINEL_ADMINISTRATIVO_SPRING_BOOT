package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.message.TicketMessageCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.message.TicketMessageDetailsDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.users.UserRegistrationDataDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketDivergencia;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketGarantia;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketMessage;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketDivergenciaRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketGarantiaRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketMessageRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.UsersRepository;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class TicketMessageService {

    @Autowired
    private TicketMessageRepository messageRepository;
    @Autowired
    private TicketGarantiaRepository garantiaRepository;
    @Autowired
    private TicketDivergenciaRepository divergenciaRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private EmailService emailService;

    private static Logger logger =
            LoggerFactory.getLogger(TicketMessageService.class);


    public TicketMessageDetailsDTO adicionarMensagemGarantia(TicketMessageCreateDTO dados) {
        TicketGarantia garantia = garantiaRepository.findById(dados.ticketId())
                .orElseThrow(() -> new RuntimeException("Ticket Garantia não encontrado"));

        var usuario = usersRepository.findById(dados.id_usuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        TicketMessage message = new TicketMessage(garantia,
                null, usuario, dados.msg(), LocalDateTime.now(),
                dados.internal());

        TicketMessage ticket = messageRepository.save(message);

        Map<String, String> variaveis = Map.of(
                "ticketId", garantia.getId().toString(),
                "status", ticket.getTicketGarantia().getStatus().toString(),
                "mensagem", message.getMessage(),
                "data",
                message.getTimestamp().format((java.time.format.DateTimeFormatter.ofPattern(
                        "dd/MM/yyyy"))),
                "usuario", message.getUsuario().getLogin()
        );

        if (!message.isInternal()) {
            emailService.enviarEmailHtml(
                    garantia.getUsuario().getEmail(),
                    "TICKET GARANTIA - " + garantia.getId() + " | NOVA MENSAGEM ENVIADA",
                    variaveis,
                    "template-email-atualizacao-status.html"
            );
        }

        logger.info("Email de mensagem de garantia enviado paraaa: " + garantia.getUsuario().getEmail());

        return new TicketMessageDetailsDTO(
                ticket.getId(),
                garantia.getId(),
                new UserRegistrationDataDTO(usuario.getId(),
                        usuario.getLogin(), usuario.getEmail(),
                        usuario.getRole()),
                ticket.getMessage(),
                ticket.isInternal(),
                ticket.getTimestamp()
        );
    }

    public TicketMessageDetailsDTO adicionarMensagemDivergencia(TicketMessageCreateDTO dados) {
        TicketDivergencia divergencia = divergenciaRepository.findById(dados.ticketId())
                .orElseThrow(() -> new RuntimeException("Ticket Divergência não encontrado"));

        var usuario = usersRepository.findById(dados.id_usuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        TicketMessage message = new TicketMessage(null,
                divergencia, usuario, dados.msg(), LocalDateTime.now(),
                dados.internal());

        TicketMessage ticket = messageRepository.save(message);

        Map<String, String> variaveis = Map.of(
                "ticketId", divergencia.getId().toString(),
                "status", ticket.getTicketDivergencia().getStatus().toString(),
                "mensagem", message.getMessage(),
                "data",
                message.getTimestamp().format((java.time.format.DateTimeFormatter.ofPattern(
                        "dd/MM/yyyy"))),
                "usuario", message.getUsuario().getLogin()
        );

        if (!message.isInternal()) {
            emailService.enviarEmailHtml(
                    divergencia.getUsuario().getEmail(),
                    "TICKET DIVERGÊNCIA - " + divergencia.getId() + " | NOVA " +
                            "MENSAGEM " +
                            "ENVIADA",
                    variaveis,
                    "template-email-atualizacao-status.html"
            );
        }

        logger.info("Email de mensagem de divergencia enviado para: " + usuario.getEmail());

        return new TicketMessageDetailsDTO(
                ticket.getId(),
                divergencia.getId(),
                new UserRegistrationDataDTO(usuario.getId(),
                        usuario.getLogin(), usuario.getEmail(),
                        usuario.getRole()),
                ticket.getMessage(),
                ticket.isInternal(),
                ticket.getTimestamp()
        );
    }

    public List<TicketMessageDetailsDTO> listarMensagensGarantia(Long garantiaId) {
        var mensagens = messageRepository.findByTicketGarantiaId(garantiaId);

        return mensagens.stream()
                .map(msg -> new TicketMessageDetailsDTO(
                        msg.getId(),
                        msg.getTicketGarantia().getId(),
                        new UserRegistrationDataDTO(
                                msg.getId(),
                                msg.getUsuario().getLogin(),
                                msg.getUsuario().getEmail(),
                                msg.getUsuario().getRole()
                        ),
                        msg.getMessage(),
                        msg.isInternal(),
                        msg.getTimestamp()
                ))
                .toList();
    }

    public List<TicketMessageDetailsDTO> listarMensagensDivergencia(Long divergenciaId) {
        var mensagens = messageRepository.findByTicketDivergenciaId(divergenciaId);

        return mensagens.stream()
                .map(msg -> new TicketMessageDetailsDTO(
                        msg.getId(),
                        msg.getTicketDivergencia().getId(),
                        new UserRegistrationDataDTO(
                                msg.getId(),
                                msg.getUsuario().getLogin(),
                                msg.getUsuario().getEmail(),
                                msg.getUsuario().getRole()
                        ),
                        msg.getMessage(),
                        msg.isInternal(),
                        msg.getTimestamp()
                ))
                .toList();
    }
}


