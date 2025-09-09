package br.com.norteautopecas.painel_administrativo_backend.infra.entity;

import br.com.norteautopecas.painel_administrativo_backend.infra.enums.StatusDivergencia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_status_historico_divergencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketStatusHistoricoDivergencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_ticket_divergencia")
    private TicketDivergencia ticketDivergencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private StatusDivergencia status;

    @Column(columnDefinition = "TEXT")
    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User usuario;

    @CreationTimestamp
    private LocalDateTime dataAtualizacao;

    public TicketStatusHistoricoDivergencia(
            TicketDivergencia ticketDivergencia,
            StatusDivergencia status,
            String mensagem,
            User usuario
    ) {
        this.ticketDivergencia = ticketDivergencia;
        this.status = status;
        this.mensagem = mensagem;
        this.usuario = usuario;
    }
}
