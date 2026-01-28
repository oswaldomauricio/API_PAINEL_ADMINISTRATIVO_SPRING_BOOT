package br.com.norteautopecas.painel_administrativo_backend.infra.entity.caixa;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StoreInformation;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.User;
import br.com.norteautopecas.painel_administrativo_backend.infra.enums.caixa.Categoria;
import br.com.norteautopecas.painel_administrativo_backend.infra.enums.caixa.TipoOperacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    private String numeroDocumento;

    @Enumerated(EnumType.STRING)
    private TipoOperacao tipoOperacao;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    private BigDecimal valor;

    private String origem;

    public Caixa( StoreInformation loja, User usuario,
                 LocalDateTime createdAt,
                 LocalDateTime updatedAt, String numeroDocumento,
                 TipoOperacao tipoOperacao, Categoria categoria, BigDecimal valor, String origem
    ) {
        this.id = null;
        this.loja = loja;
        this.usuario = usuario;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.numeroDocumento = numeroDocumento;
        this.tipoOperacao = tipoOperacao;
        this.categoria = categoria;
        this.valor = valor;
        this.origem = origem;
    }

}
