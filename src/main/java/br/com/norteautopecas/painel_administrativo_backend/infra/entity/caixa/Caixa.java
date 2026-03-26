package br.com.norteautopecas.painel_administrativo_backend.infra.entity.caixa;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StoreInformation;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.User;
import br.com.norteautopecas.painel_administrativo_backend.infra.enums.caixa.TipoOperacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "Caixa")
@Table(name = "Caixa")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Caixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_loja", nullable = false)
    private StoreInformation loja;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User usuario;

    /**
     * Data de inserção lógica do lançamento (pode diferir da data real do registro).
     */
    @Column(name = "data_insercao", nullable = false)
    private LocalDate dataInsercao;

    /**
     * Momento em que o registro foi criado no sistema.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "numero_documento", length = 10)
    @Size(max = 10)
    private String numeroDocumento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_operacao", nullable = false)
    private TipoOperacao tipoOperacao;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaCaixa categoria;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(length = 50)
    @Size(max = 50)
    private String origem;

    public Caixa(StoreInformation loja,
                 User usuario,
                 LocalDate dataInsercao,
                 String numeroDocumento,
                 TipoOperacao tipoOperacao,
                 CategoriaCaixa categoria,
                 BigDecimal valor,
                 String origem) {
        this.loja = loja;
        this.usuario = usuario;
        this.dataInsercao = dataInsercao;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.numeroDocumento = numeroDocumento;
        this.tipoOperacao = tipoOperacao;
        this.categoria = categoria;
        this.valor = valor;
        this.origem = origem;
    }
}
