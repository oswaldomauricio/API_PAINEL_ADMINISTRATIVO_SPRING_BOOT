package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.divergencia.EstatisticasTicketDivergenciaDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.divergencia.TicketDivergenciaCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.divergencia.TicketDivergenciaDetailsDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.divergencia.TicketDivergenciaFilterDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.garantia.EstatisticasTicketGarantiaDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.*;
import br.com.norteautopecas.painel_administrativo_backend.infra.enums.StatusDivergencia;
import br.com.norteautopecas.painel_administrativo_backend.infra.enums.StatusGarantia;
import br.com.norteautopecas.painel_administrativo_backend.infra.mapper.TicketDivergenciaMapper;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.StoreInformationRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketDivergenciaRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketDivergenciaSpecifications;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.UsersRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.ValidateException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TicketDivergenciaService {

    @Autowired
    private TicketDivergenciaRepository ticketDivergenciaRepository;

    @Autowired
    private StoreInformationRepository storeInformationRepository;

    @Autowired
    private TicketDivergenciaMapper ticketDivergenciaMapper;

    @Autowired
    private StoreService storeService;

    @Autowired
    private UsersRepository usersRepository;

    private TicketDivergenciaSpecifications ticketSpecifications;

    @Autowired
    private EmailService emailService;


    public TicketDivergenciaDetailsDTO cadastrarTicket(TicketDivergenciaCreateDTO dados) {
        var loja = storeInformationRepository.findByLoja(dados.loja())
                .orElseThrow(() -> new RuntimeException("Loja não encontrada: " + dados.loja()));

        var usuarioCadastroTicket = usersRepository.findById(dados.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Ticket ticket = new Ticket(
                loja,
                dados.fornecedor(),
                dados.cpfCnpj(),
                dados.nota(),
                dados.descricao(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                null);

        List<Produto> produtos = dados.produtos().stream()
                .map(produtoDTO -> {
                    Produto produto = new Produto(
                            produtoDTO.codigoProduto(),
                            produtoDTO.quantidade(),
                            produtoDTO.tipo(),
                            null,
                            null
                    );
                    return produto;
                })
                .toList();

        TicketDivergencia ticketDivergencia = new TicketDivergencia(
                null,
                ticket,
                usuarioCadastroTicket,
                new ArrayList<>()
        );

        ticketDivergencia.setStatus(StatusDivergencia.NOVO);

        TicketDivergencia finalTicketDivergencia = ticketDivergencia;
        produtos.forEach(p -> p.setTicketDivergencia(finalTicketDivergencia));
        ticketDivergencia.getProdutos().addAll(produtos);

        ticketDivergencia = ticketDivergenciaRepository.save(ticketDivergencia);

        String produtosHtml = ticketDivergencia.getProdutos().stream()
                .map(prod -> "<li>" + prod.getCodigoProduto() + " - Qtd: " + prod.getQuantidade() + "</li>")
                .reduce("", (acc, item) -> acc + item);

        produtosHtml = "<ul>" + produtosHtml + "</ul>";

        Map<String, String> variaveis = Map.of(
                "ticketId", ticketDivergencia.getId().toString(),
                "tipo", "Divergência",
                "loja", ticketDivergencia.getTicket().getLoja().getLoja().toString(),
                "fornecedor", ticketDivergencia.getTicket().getFornecedor(),
                "data",
                ticketDivergencia.getTicket().getDataAtualizacao().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                "usuario", ticketDivergencia.getUsuario().getLogin(),
                "descricao", ticketDivergencia.getTicket().getDescricao(),
                "produtos", produtosHtml
        );

        emailService.enviarEmailHtml(
                usuarioCadastroTicket.getEmail(),
                "TICKET DIVERGÊNCIA - " + ticketDivergencia.getId() + " | NOVO TICKET CRIADO",
                variaveis,
                "template-email-criando-ticket.html"
        );

        return ticketDivergenciaMapper.toDetailsDTO(ticketDivergencia);
    }

    public Page<TicketDivergenciaDetailsDTO> buscarTodosTickets(
            TicketDivergenciaFilterDTO filtro,
            Pageable pageable) {

        Page<TicketDivergencia> ticketsPage = ticketDivergenciaRepository.findAll(
                TicketDivergenciaSpecifications.fornecedorContains(filtro.fornecedor())
                        .and(TicketDivergenciaSpecifications.ticketIdEqual(filtro.ticketId()))
                        .and(TicketDivergenciaSpecifications.cpfCnpjEquals(filtro.cpfCnpj()))
                        .and(TicketDivergenciaSpecifications.notaEquals(filtro.nota()))
                        .and(TicketDivergenciaSpecifications.dataSolicitacaoBetween(filtro.dataInicio(), filtro.dataFim()))
                        .and(TicketDivergenciaSpecifications.statusEquals(filtro.status()))
                        .and(TicketDivergenciaSpecifications.lojaEquals(filtro.loja())),
                pageable);

        return ticketsPage.map(ticketDivergenciaMapper::toDetailsDTO);
    }

    public EstatisticasTicketDivergenciaDTO estatisticasTicketDivergencia(Integer loja) {
        Integer totalTickets;
        Integer ticketsAbertos;
        Integer ticketsEmAndamento;
        Integer ticketsConcluidos;
        Integer ticketsCancelados;

        StoreInformation store =
                storeInformationRepository.findByLoja(loja).orElseThrow(() -> new EntityNotFoundException("Loja não encontrada: " + loja));

        totalTickets = ticketDivergenciaRepository.countByTicket_Loja(store);
        ticketsAbertos =
                ticketDivergenciaRepository.countByTicket_LojaAndStatus(store,
                        StatusDivergencia.NOVO);

        List<StatusDivergencia> excludedStatuses =
                Arrays.asList(StatusDivergencia.NOVO, StatusDivergencia.CONCLUIDO,
                        StatusDivergencia.CANCELADO);
        ticketsEmAndamento =
                ticketDivergenciaRepository.countByTicket_LojaAndStatusNotIn(store, excludedStatuses);

        ticketsConcluidos = ticketDivergenciaRepository.countByTicket_LojaAndStatus(store,
                StatusDivergencia.CONCLUIDO);


        ticketsCancelados =
                ticketDivergenciaRepository.countByTicket_LojaAndStatus(store,
                        StatusDivergencia.CANCELADO);


        return new EstatisticasTicketDivergenciaDTO(
                totalTickets,
                ticketsAbertos,
                ticketsEmAndamento,
                ticketsConcluidos,
                ticketsCancelados
        );
    }

}
