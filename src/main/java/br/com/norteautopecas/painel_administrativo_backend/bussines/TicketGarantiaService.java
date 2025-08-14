package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.*;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Produto;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StoreInformation;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Ticket;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketGarantia;
import br.com.norteautopecas.painel_administrativo_backend.infra.mapper.TicketGarantiaMapper;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.StoreInformationRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.StoreRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketGarantiaRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.UsersRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

        return ticketGarantiaMapper.toDetailsDTO(ticketGarantia);
    }

    public ResponseEntity<TicketGarantiaDetailsDTO> buscarTicketPorId(Long id) {

        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Optional<TicketGarantia> ticketGarantia =
                ticketGarantiaRepository.findById(id);

        if (ticketGarantia.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ticketGarantiaMapper.toDetailsDTO(ticketGarantia.get()));
    }

    public Page<TicketGarantiaDetailsDTO> buscarTodosTickets(Pageable pageable) {
        Page<TicketGarantia> ticketsPage = ticketGarantiaRepository.findAll(pageable);

        return ticketsPage.map(ticketGarantiaMapper::toDetailsDTO);
    }

    public Page<TicketGarantiaDetailsDTO> buscarTicketsPorLoja(Integer loja, Pageable pageable) {
        if (loja == null || loja <= 0) {
            throw new ValidateException("Loja não informada");
        }

        StoreInformation storeInformation = storeService.getStoreByLoja(loja);

        Page<TicketGarantia> ticketsPage = ticketGarantiaRepository.findByTicket_Loja(storeInformation, pageable);
        return ticketsPage.map(ticketGarantiaMapper::toDetailsDTO);
    }

}
