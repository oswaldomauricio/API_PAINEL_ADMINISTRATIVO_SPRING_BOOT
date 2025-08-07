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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TicketDivergencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User usuario;

    @OneToMany(mappedBy = "ticketDivergencia", cascade = CascadeType.ALL)
    private List<Produto> produtos = new ArrayList<>();

//    @OneToMany(mappedBy = "ticketDivergencia", cascade = CascadeType.ALL)
//    private List<Arquivo> arquivos = new ArrayList<>();

    // Getters e setters
}
