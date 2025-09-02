package br.com.norteautopecas.painel_administrativo_backend.infra.entity;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.users.UserRegistrationDataDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_message")
@NoArgsConstructor
@AllArgsConstructor
public class TicketMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_garantia_id")
    private TicketGarantia ticketGarantia;

    @ManyToOne
    @JoinColumn(name = "ticket_divergencia_id")
    private TicketDivergencia ticketDivergencia;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    @Column(columnDefinition = "TEXT")
    private String message;

    private LocalDateTime timestamp;

    private boolean isInternal;

    public TicketMessage(
            TicketGarantia ticketGarantia,
            TicketDivergencia ticketDivergencia,
            User usuario,
            String message,
            LocalDateTime timestamp,
            boolean isInternal
    ) {
        this.ticketGarantia = ticketGarantia;
        this.ticketDivergencia = ticketDivergencia;
        this.usuario = usuario;
        this.message = message;
        this.timestamp = timestamp;
        this.isInternal = isInternal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TicketGarantia getTicketGarantia() {
        return ticketGarantia;
    }

    public void setTicketGarantia(TicketGarantia ticketGarantia) {
        this.ticketGarantia = ticketGarantia;
    }

    public TicketDivergencia getTicketDivergencia() {
        return ticketDivergencia;
    }

    public void setTicketDivergencia(TicketDivergencia ticketDivergencia) {
        this.ticketDivergencia = ticketDivergencia;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isInternal() {
        return isInternal;
    }

    public void setInternal(boolean internal) {
        isInternal = internal;
    }
}
