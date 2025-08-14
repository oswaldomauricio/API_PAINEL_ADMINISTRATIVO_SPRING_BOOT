package br.com.norteautopecas.painel_administrativo_backend.infra.mapper;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.ProdutoCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketDivergenciaDetailsDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketDivergencia;
import org.springframework.stereotype.Component;

@Component
public class TicketDivergenciaMapper {
    public TicketDivergenciaDetailsDTO toDetailsDTO(TicketDivergencia ticketDivergencia) {
        return new TicketDivergenciaDetailsDTO(
                ticketDivergencia.getId(),
                ticketDivergencia.getTicket().getLoja().getLoja(),
                ticketDivergencia.getTicket().getFornecedor(),
                ticketDivergencia.getTicket().getCpfCnpj(),
                ticketDivergencia.getTicket().getNota(),
                ticketDivergencia.getTicket().getDescricao(),
                ticketDivergencia.getUsuario().getUsername(),
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
