package br.com.norteautopecas.painel_administrativo_backend.infra.mapper;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.ProdutoCreateDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketGarantiaDetailsDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketGarantia;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TicketGarantiaMapper {
    public TicketGarantiaDetailsDTO toDetailsDTO(TicketGarantia tg) {
        return new TicketGarantiaDetailsDTO(
                tg.getId(),
                tg.getNomeCliente(),
                tg.getTicket().getLoja().getLoja(),
                tg.getTicket().getFornecedor(),
                tg.getTicket().getCpfCnpj(),
                tg.getTicket().getNota(),
                tg.getTicket().getDescricao(),
                tg.getUsuario().getLogin(),
                tg.getTicket().getDataSolicitacao(),
                tg.getTicket().getDataAtualizacao(),
                tg.getTicket().getDiasEmAberto(),
                tg.getTicket().getStatus(),
                tg.getProdutos().stream()
                        .map(p -> new ProdutoCreateDTO(
                                p.getCodigoProduto(),
                                p.getQuantidade(),
                                p.getTipo(),
                                p.getValorUnitario()
                        ))
                        .toList()
        );
    }

    public List<TicketGarantiaDetailsDTO> toDetailsDTOList(List<TicketGarantia> tickets) {
        return tickets.stream()
                .map(this::toDetailsDTO)
                .collect(Collectors.toList());
    }
}
