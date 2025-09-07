package br.com.norteautopecas.painel_administrativo_backend.infra.entity;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.TicketGarantiaDetailsDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_status_historico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketStatusHistorico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_ticket_divergencia")
    private TicketDivergencia ticketDivergencia;

    @ManyToOne
    @JoinColumn(name = "id_ticket_garantia")
    private TicketGarantia ticketGarantia;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private Status status;

    @Column(columnDefinition = "TEXT")
    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User usuario;

    @CreationTimestamp
    private LocalDateTime dataAtualizacao;

    public TicketStatusHistorico(
            TicketDivergencia ticketDivergencia,
            TicketGarantia ticketGarantia,
            Status status,
            String mensagem,
            User usuario
    ) {
        this.ticketDivergencia = ticketDivergencia;
        this.ticketGarantia = ticketGarantia;
        this.status = status;
        this.mensagem = mensagem;
        this.usuario = usuario;
    }
}
