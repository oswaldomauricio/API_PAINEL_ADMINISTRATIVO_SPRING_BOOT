package br.com.norteautopecas.painel_administrativo_backend.infra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Garantia")
@Table(name = "ticket_garantia")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TicketGarantia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCliente;

    @Embedded
    @AttributeOverride(name = "loja", column = @Column(name = "id_loja"))
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User usuario;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private StatusGarantia status = StatusGarantia.NOVO;

    @OneToMany(mappedBy = "ticketGarantia", cascade = CascadeType.ALL)
    private List<Produto> produtos = new ArrayList<>();

    @OneToMany(mappedBy = "ticketGarantia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketMessage> messages = new ArrayList<>();

    public TicketGarantia(
            Long id,
            String nomeCliente,
            Ticket ticket,
            User usuario,
            List<Produto> produtos
    ) {
        this.id = null;
        this.nomeCliente = nomeCliente;
        this.ticket = ticket;
        this.usuario = usuario;
    }

}
