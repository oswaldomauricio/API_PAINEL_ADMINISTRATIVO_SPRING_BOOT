package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.*;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.*;
import br.com.norteautopecas.painel_administrativo_backend.infra.enums.StatusDivergencia;
import br.com.norteautopecas.painel_administrativo_backend.infra.mapper.TicketDivergenciaMapper;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.StoreInformationRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.TicketDivergenciaRepository;
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

        return ticketDivergenciaMapper.toDetailsDTO(ticketDivergencia);
    }

    public ResponseEntity<TicketDivergenciaDetailsDTO> buscarTicketPorId(Long id) {

        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Optional<TicketDivergencia> ticketDivergencia =
                ticketDivergenciaRepository.findById(id);

        if (ticketDivergencia.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ticketDivergenciaMapper.toDetailsDTO(ticketDivergencia.get()));
    }

    public Page<TicketDivergenciaDetailsDTO> buscarTodosTickets(Pageable pageable) {
        Page<TicketDivergencia> ticketsPage = ticketDivergenciaRepository.findAll(pageable);

        return ticketsPage.map(ticketDivergenciaMapper::toDetailsDTO);
    }

    public Page<TicketDivergenciaDetailsDTO> buscarTicketsPorLoja(Integer loja, Pageable pageable) {
        if (loja == null || loja <= 0) {
            throw new ValidateException("Loja não informada");
        }

        StoreInformation storeInformation = storeService.getStoreByLoja(loja);

        Page<TicketDivergencia> ticketsPage = ticketDivergenciaRepository.findByTicket_Loja(storeInformation, pageable);
        return ticketsPage.map(ticketDivergenciaMapper::toDetailsDTO);
    }


}
