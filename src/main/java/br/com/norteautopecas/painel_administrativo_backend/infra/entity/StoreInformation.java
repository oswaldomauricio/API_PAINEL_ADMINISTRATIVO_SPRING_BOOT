package br.com.norteautopecas.painel_administrativo_backend.infra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "LojaInformation")
@Table(name = "Lojas_information")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class StoreInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private Integer loja;

    @Column(name = "nomeLoja")
    private String nomeLoja;

    @Column(name = "enderecoLoja")
    private String enderecoLoja;

    @Column(name = "email")
    private String email;

    @Column(name = "whatsapp")
    private String whatsapp;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "segmentacao")
    private String segmentacao;

    @Column(name = "segmentacao2")
    private String segmentacao2;

    @Column(name = "estado")
    private String estado;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "sigla")
    private String sigla;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "horario")
    private String horario;
}
