package br.com.norteautopecas.painel_administrativo_backend.infra.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_status_historico_garantia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketStatusHistoricoGarantia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_ticket_garantia")
    private TicketGarantia ticketGarantia;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private StatusGarantia status;

    @Column(columnDefinition = "TEXT")
    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User usuario;

    @CreationTimestamp
    private LocalDateTime dataAtualizacao;

    public TicketStatusHistoricoGarantia(
            TicketGarantia ticketGarantia,
            StatusGarantia status,
            String mensagem,
            User usuario
    ) {
        this.ticketGarantia = ticketGarantia;
        this.status = status;
        this.mensagem = mensagem;
        this.usuario = usuario;
    }
}
