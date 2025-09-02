package br.com.norteautopecas.painel_administrativo_backend.infra.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Divergencia")
@Table(name = "ticket_divergencia")
@Getter
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
