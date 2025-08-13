package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.ProdutoCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketGarantiaCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketGarantiaDetailsDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Produto;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StoreInformation;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Ticket;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketGarantia;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketGarantiaRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketGarantiaService {

    @Autowired
    private TicketGarantiaRepository ticketGarantiaRepository;

    @Autowired
    private StoreService storeService;

    @Autowired
    private UsersRepository usersRepository;

    public TicketGarantiaDetailsDTO cadastrarTicket(TicketGarantiaCreateDTO dados) {
        var loja = storeService.getStoreByLoja(dados.loja());

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
                            null,
                            null,
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

        TicketGarantia finalTicketGarantia = ticketGarantia;
        produtos.forEach(p -> p.setTicketGarantia(finalTicketGarantia));
        ticketGarantia.getProdutos().addAll(produtos);

        ticketGarantia = ticketGarantiaRepository.save(ticketGarantia);

        return new TicketGarantiaDetailsDTO(
                ticketGarantia.getId(),
                ticketGarantia.getNomeCliente(),
                ticketGarantia.getTicket().getLoja().toString(),
                ticketGarantia.getTicket().getFornecedor(),
                ticketGarantia.getTicket().getCpfCnpj(),
                ticketGarantia.getTicket().getNota(),
                ticketGarantia.getTicket().getDescricao(),
                ticketGarantia.getUsuario().getId(),
                ticketGarantia.getTicket().getDataSolicitacao(),
                ticketGarantia.getTicket().getDataAtualizacao(),
                ticketGarantia.getTicket().getDiasEmAberto(),
                ticketGarantia.getTicket().getStatus(),
                ticketGarantia.getProdutos().stream()
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
