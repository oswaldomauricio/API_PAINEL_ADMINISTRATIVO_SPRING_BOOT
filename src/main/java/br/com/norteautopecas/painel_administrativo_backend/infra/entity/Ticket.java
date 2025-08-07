package br.com.norteautopecas.painel_administrativo_backend.infra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @CreationTimestamp
    private LocalDateTime dataSolicitacao;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

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
}