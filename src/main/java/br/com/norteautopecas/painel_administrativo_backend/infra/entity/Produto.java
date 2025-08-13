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

    public Produto(
            String codigoProduto,
            Integer quantidade,
            Tipo tipo,
            BigDecimal valorUnitario,
            TicketGarantia ticketGarantia
    ) {
        this.id = null;
        this.codigoProduto = codigoProduto;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.valorUnitario = valorUnitario;
        this.ticketGarantia = ticketGarantia;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}
