package br.com.norteautopecas.painel_administrativo_backend.infra.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Divergencia")
@Table(name = "ticket_divergencia")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TicketDivergencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "loja", column = @Column(name = "id_loja"))
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User usuario;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private StatusDivergencia status = StatusDivergencia.NOVO;

    @OneToMany(mappedBy = "ticketDivergencia", cascade = CascadeType.ALL)
    private List<Produto> produtos = new ArrayList<>();

    @OneToMany(mappedBy = "ticketDivergencia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketMessage> messages = new ArrayList<>();

    public TicketDivergencia(
            Long id,
            Ticket ticket,
            User usuario,
            List<Produto> produtos
    ) {
        this.id = null;
        this.ticket = ticket;
        this.usuario = usuario;
    }

}
