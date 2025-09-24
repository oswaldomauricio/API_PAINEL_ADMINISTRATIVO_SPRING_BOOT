package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.garantia.TicketGarantiaCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.garantia.TicketGarantiaDetailsDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.garantia.TicketGarantiaFilterDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.*;
import br.com.norteautopecas.painel_administrativo_backend.infra.enums.StatusGarantia;
import br.com.norteautopecas.painel_administrativo_backend.infra.mapper.TicketGarantiaMapper;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TicketGarantiaService {

    @Autowired
    private TicketGarantiaRepository ticketGarantiaRepository;

    @Autowired
    private StoreInformationRepository storeInformationRepository;

    @Autowired
    private StoreService storeService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TicketGarantiaMapper ticketGarantiaMapper;

    private TicketGarantiaSpecifications ticketSpecifications;

    @Autowired
    private EmailService emailService;

    public TicketGarantiaDetailsDTO cadastrarTicket(TicketGarantiaCreateDTO dados) {
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
                null
        );

        List<Produto> produtos = dados.produtos().stream()
                .map(produtoDTO -> {
                    Produto produto = new Produto(
                            produtoDTO.codigoProduto(),
                            produtoDTO.quantidade(),
                            null,
                            produtoDTO.valorUnitario(),
                            null
                    );
                    return produto;
                })
                .toList();

        TicketGarantia ticketGarantia = new TicketGarantia(
                null,
                dados.nomeCliente(),
                ticket,
                usuarioCadastroTicket,
                new ArrayList<>()
        );

        ticketGarantia.setStatus(StatusGarantia.NOVO);

        TicketGarantia finalTicketGarantia = ticketGarantia;
        produtos.forEach(p -> p.setTicketGarantia(finalTicketGarantia));
        ticketGarantia.getProdutos().addAll(produtos);

        ticketGarantia = ticketGarantiaRepository.save(ticketGarantia);

        //aqui eu criei uma forma pra personalizar os produtos para aparecer
        // legal no email.
        String produtosHtml = ticketGarantia.getProdutos().stream()
                .map(prod -> "<li>" + prod.getCodigoProduto() + " - Qtd: " + prod.getQuantidade() + "</li>")
                .reduce("", (acc, item) -> acc + item);

        produtosHtml = "<ul>" + produtosHtml + "</ul>";

        Map<String, String> variaveis = Map.of(
                "ticketId", ticketGarantia.getId().toString(),
                "tipo", "Garantia",
                "loja", ticketGarantia.getTicket().getLoja().getLoja().toString(),
                "fornecedor", ticketGarantia.getTicket().getFornecedor(),
                "data",
                ticketGarantia.getTicket().getDataAtualizacao().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                "usuario", ticketGarantia.getUsuario().getLogin(),
                "descricao", ticketGarantia.getTicket().getDescricao(),
                "produtos", produtosHtml
        );

        emailService.enviarEmailHtml(
                usuarioCadastroTicket.getEmail(),
                "TICKET GARANTIA - " + ticketGarantia.getId() + " | NOVO TICKET CRIADO",
                variaveis,
                "template-email-criando-ticket.html"
        );

        return ticketGarantiaMapper.toDetailsDTO(ticketGarantia);
    }

    public Page<TicketGarantiaDetailsDTO> buscarTodosTickets(
            TicketGarantiaFilterDTO filtro,
            Pageable pageable) {

        Page<TicketGarantia> ticketsPage =
                ticketGarantiaRepository.findAll(
                        TicketGarantiaSpecifications.fornecedorContains(filtro.fornecedor())
                                .and(TicketGarantiaSpecifications.ticketIdEqual(filtro.ticketId()))
                                .and(TicketGarantiaSpecifications.nomeClienteContains(filtro.nomeCliente()))
                                .and(TicketGarantiaSpecifications.cpfCnpjEquals(filtro.cpfCnpj()))
                                .and(TicketGarantiaSpecifications.notaEquals(filtro.nota()))
                                .and(TicketGarantiaSpecifications.dataSolicitacaoBetween(filtro.dataInicio(), filtro.dataFim()))
                                .and(TicketGarantiaSpecifications.statusEquals(filtro.status()))
                                .and(TicketGarantiaSpecifications.lojaEquals(filtro.loja())),
                        pageable);

        return ticketsPage.map(ticketGarantiaMapper::toDetailsDTO);
    }
}
