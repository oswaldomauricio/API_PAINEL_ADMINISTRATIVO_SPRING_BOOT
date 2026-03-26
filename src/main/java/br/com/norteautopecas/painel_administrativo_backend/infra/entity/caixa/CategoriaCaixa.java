package br.com.norteautopecas.painel_administrativo_backend.infra.entity.caixa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "CategoriaCaixa")
@Table(name = "categorias_caixa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CategoriaCaixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank
    @Size(max = 100)
    private String nome;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public CategoriaCaixa(String nome) {
        this.nome = nome;
        this.ativo = true;
        this.createdAt = LocalDateTime.now();
    }
}
