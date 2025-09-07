package br.com.norteautopecas.painel_administrativo_backend.infra.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Ticket {

    @ManyToOne
    @JoinColumn(name = "id_loja", nullable = false)
    private StoreInformation loja;

    @Column(nullable = false)
    private String fornecedor;

    @Column(name = "cpf/cnpj", nullable = false)
    private String cpfCnpj;

    @Column(nullable = false)
    private String nota;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private Status status = Status.NOVO;

    @CreationTimestamp
    private LocalDateTime dataSolicitacao = LocalDateTime.now();

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;


    public Ticket(
            StoreInformation loja,
            String fornecedor,
            String cpfCnpj,
            String nota,
            String descricao,
            LocalDateTime dataSolicitacao,
            LocalDateTime dataAtualizacao,
            Long diasEmAberto,
            Status status
    ) {
        this.loja = loja;
        this.fornecedor = fornecedor;
        this.cpfCnpj = cpfCnpj;
        this.nota = nota;
        this.descricao = descricao;
        this.dataSolicitacao = dataSolicitacao;
        this.dataAtualizacao = dataAtualizacao;
        this.getDiasEmAberto();
        this.status = Status.NOVO;
    }

    @Transient
    public long getDiasEmAberto() {
        return ChronoUnit.DAYS.between(this.dataSolicitacao, LocalDateTime.now());
    }

    public StoreInformation getLoja() {
        return loja;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getNota() {
        return nota;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public Status getStatus() {
        return status;
    }


}

