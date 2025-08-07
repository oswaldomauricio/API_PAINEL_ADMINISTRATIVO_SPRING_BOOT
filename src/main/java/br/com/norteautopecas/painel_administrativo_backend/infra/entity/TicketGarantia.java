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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TicketGarantia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCliente;

    @Embedded
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User usuario;

    @OneToMany(mappedBy = "ticketGarantia", cascade = CascadeType.ALL)
    private List<Produto> produtos = new ArrayList<>();

//    @OneToMany(mappedBy = "ticketGarantia", cascade = CascadeType.ALL)
//    private List<Arquivo> arquivos = new ArrayList<>();


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
