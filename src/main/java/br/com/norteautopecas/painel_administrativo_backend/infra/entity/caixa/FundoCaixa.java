package br.com.norteautopecas.painel_administrativo_backend.infra.entity.caixa;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StoreInformation;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "FundoCaixa")
@Table(name = "fundo_caixa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class FundoCaixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Loja à qual esse fundo pertence (cada loja tem um único fundo de caixa).
     */
    @OneToOne
    @JoinColumn(name = "id_loja", nullable = false, unique = true)
    private StoreInformation loja;

    /**
     * Valor do fundo de caixa da loja. Apenas informativo — não altera saldo.
     */
    @Column(name = "valor_fundo", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorFundo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public FundoCaixa(StoreInformation loja, BigDecimal valorFundo) {
        this.loja = loja;
        this.valorFundo = valorFundo;
        this.createdAt = LocalDateTime.now();
    }
}
