package br.com.norteautopecas.painel_administrativo_backend.infra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Garantia")
@Table(name = "ticket_garantia")
@Getter
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
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
