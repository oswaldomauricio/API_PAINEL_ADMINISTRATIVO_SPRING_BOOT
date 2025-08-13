package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.*;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Produto;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StoreInformation;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Ticket;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketGarantia;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.StoreInformationRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.StoreRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketGarantiaRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.UsersRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public TicketGarantiaDetailsDTO cadastrarTicket(TicketGarantiaCreateDTO dados) {
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

        TicketGarantia finalTicketGarantia = ticketGarantia;
        produtos.forEach(p -> p.setTicketGarantia(finalTicketGarantia));
        ticketGarantia.getProdutos().addAll(produtos);

        ticketGarantia = ticketGarantiaRepository.save(ticketGarantia);

        return new TicketGarantiaDetailsDTO(
                ticketGarantia.getId(),
                ticketGarantia.getNomeCliente(),
                ticketGarantia.getTicket().getLoja().getLoja(),
                ticketGarantia.getTicket().getFornecedor(),
                ticketGarantia.getTicket().getCpfCnpj(),
                ticketGarantia.getTicket().getNota(),
                ticketGarantia.getTicket().getDescricao(),
                usuarioCadastroTicket.getLogin(),
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

    public ResponseEntity<TicketGarantiaDetailsDTO> buscarTicketPorId(Long id) {

        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        TicketGarantia ticketGarantia = ticketGarantiaRepository.findById(id)
                .orElseThrow(() -> new ValidateException("Ticket de garantia com id " + id + " não encontrado"));

        if (ticketGarantia == null) {
            return ResponseEntity.notFound().build();
        }

        StoreInformation lojaInfo = ticketGarantia.getTicket() != null
                ? ticketGarantia.getTicket().getLoja()
                : null;

        Integer numeroLoja = (lojaInfo != null) ? lojaInfo.getLoja() : null;

        return ResponseEntity.ok(new TicketGarantiaDetailsDTO(
                ticketGarantia.getId(),
                ticketGarantia.getNomeCliente(),
                numeroLoja,
                ticketGarantia.getTicket().getFornecedor(),
                ticketGarantia.getTicket().getCpfCnpj(),
                ticketGarantia.getTicket().getNota(),
                ticketGarantia.getTicket().getDescricao(),
                ticketGarantia.getUsuario().getLogin(),
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
        ));
    }


}
