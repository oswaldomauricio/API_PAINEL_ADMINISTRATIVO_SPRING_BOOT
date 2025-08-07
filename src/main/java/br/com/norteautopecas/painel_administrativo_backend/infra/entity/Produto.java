package br.com.norteautopecas.painel_administrativo_backend.infra.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "Produto")
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_produto", nullable = false)
    private String codigoProduto;
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;
    private Tipo tipo;
    @Column(name = "valor_unitario")
    private BigDecimal valorUnitario;

    @ManyToOne
    private TicketGarantia ticketGarantia;

    @ManyToOne
    private TicketDivergencia ticketDivergencia;
}
