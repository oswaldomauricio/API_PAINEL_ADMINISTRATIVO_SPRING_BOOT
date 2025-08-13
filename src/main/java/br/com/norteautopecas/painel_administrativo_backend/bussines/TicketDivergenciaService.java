package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.*;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Produto;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Ticket;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketDivergencia;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.StoreInformationRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketDivergenciaRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketDivergenciaService {

    @Autowired
    private TicketDivergenciaRepository ticketDivergenciaRepository;

    @Autowired
    private StoreInformationRepository storeInformationRepository;

    @Autowired
    private StoreService storeService;

    @Autowired
    private UsersRepository usersRepository;

    public TicketDivergenciaDetailsDTO cadastrarTicket(TicketDivergenciaCreateDTO dados) {
        var loja = storeInformationRepository.findByLoja(dados.loja());

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
                null,
                null
        );

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

        TicketDivergencia finalTicketDivergencia = ticketDivergencia;
        produtos.forEach(p -> p.setTicketDivergencia(finalTicketDivergencia));
        ticketDivergencia.getProdutos().addAll(produtos);

        ticketDivergencia = ticketDivergenciaRepository.save(ticketDivergencia);

        return new TicketDivergenciaDetailsDTO(
                ticketDivergencia.getId(),
                dados.loja(),
                ticketDivergencia.getTicket().getFornecedor(),
                ticketDivergencia.getTicket().getCpfCnpj(),
                ticketDivergencia.getTicket().getNota(),
                ticketDivergencia.getTicket().getDescricao(),
                usuarioCadastroTicket.getLogin(),
                ticketDivergencia.getTicket().getDataSolicitacao(),
                ticketDivergencia.getTicket().getDataAtualizacao(),
                ticketDivergencia.getTicket().getDiasEmAberto(),
                ticketDivergencia.getTicket().getStatus(),
                ticketDivergencia.getProdutos().stream()
                        .map(p -> new ProdutoCreateDTO(
                                p.getCodigoProduto(),
                                p.getQuantidade(),
                                p.getTipo(),
                                p.getValorUnitario()
                        ))
                        .toList()
        );
    }


}
