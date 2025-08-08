package br.com.norteautopecas.painel_administrativo_backend.infra.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket_files")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TicketFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_id", nullable = false)
    private Long ticketId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "url", nullable = false)
    private String url;

    public TicketFiles(Long ticketId, String fileName, Long fileSize, String url) {
        this.id = null;
        this.ticketId = ticketId;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.url = url;
    }

}
