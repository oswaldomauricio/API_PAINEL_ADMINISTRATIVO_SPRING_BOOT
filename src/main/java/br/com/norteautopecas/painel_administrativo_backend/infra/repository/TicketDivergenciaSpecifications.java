package br.com.norteautopecas.painel_administrativo_backend.infra.repository;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.TicketDivergencia;
import br.com.norteautopecas.painel_administrativo_backend.infra.enums.StatusDivergencia;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TicketDivergenciaSpecifications {
    public static Specification<TicketDivergencia> ticketIdEqual(Long ticketId) {
        return ((root, query, builder) -> {
            if (ObjectUtils.isEmpty(ticketId)) {
                return null;
            }

            return builder.equal(root.get("id"), ticketId);
        });
    }

    public static Specification<TicketDivergencia> lojaEquals(Integer loja) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(loja)) {
                return null;
            }

            return builder.equal(root.get("ticket").get("loja").get("loja"),
                    loja);
        };
    }

    public static Specification<TicketDivergencia> dataSolicitacaoBetween(LocalDate dataInicio, LocalDate dataFim) {
        return (root, query, builder) -> {
            if (dataInicio == null && dataFim == null) {
                return builder.conjunction();
            }

            LocalDateTime inicio = (dataInicio != null)
                    ? dataInicio.atStartOfDay()
                    : LocalDate.now().atStartOfDay();

            LocalDateTime fim = (dataFim != null)
                    ? dataFim.atTime(23, 59, 59)
                    : LocalDate.now().atTime(23, 59, 59);

            return builder.between(root.get("ticket").get("dataSolicitacao"), inicio, fim);
        };
    }

    public static Specification<TicketDivergencia> statusEquals(StatusDivergencia status) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(status)) {
                return null;
            }

            return builder.equal(root.get("status"), status);
        };
    }

    public static Specification<TicketDivergencia> notaEquals(String nota) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(nota)) {
                return null;
            }

            return builder.equal(root.get("ticket").get("nota"), nota);
        };
    }

    public static Specification<TicketDivergencia> fornecedorContains(String fornecedor) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(fornecedor)) {
                return null;
            }
            return builder.like(builder.lower(root.get("ticket").get("fornecedor")), "%" + fornecedor.toLowerCase() + "%");
        };
    }


    public static Specification<TicketDivergencia> cpfCnpjEquals(String cpfCnpj) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(cpfCnpj)) {
                return null;
            }
            return builder.like(builder.lower(root.get("ticket").get("cpfCnpj")), cpfCnpj);
        };
    }
}
