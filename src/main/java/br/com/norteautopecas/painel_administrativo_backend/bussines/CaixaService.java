package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa.InserirCaixaDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.caixa.Caixa;
import br.com.norteautopecas.painel_administrativo_backend.infra.enums.caixa.Categoria;
import br.com.norteautopecas.painel_administrativo_backend.infra.enums.caixa.TipoOperacao;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.StoreInformationRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.UsersRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.caixa.CaixaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CaixaService {
    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreInformationRepository storeInformationRepository;

    @Autowired
    private CaixaRepository caixaRepository;

    @Autowired
    private UsersRepository usersRepository;

    public ResponseEntity inserirCaixa(InserirCaixaDTO dados) {

        try {

            var loja = storeInformationRepository.findByLoja(dados.loja())
                    .orElseThrow(() -> new RuntimeException("Loja não encontrada: " + dados.loja()));

            var usuario = usersRepository.findById(dados.usuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            Caixa caixa = new Caixa(
                    loja,
                    usuario,
                    dados.data(),
                    LocalDateTime.now(),
                    dados.numeroDocumento(),
                    TipoOperacao.fromString(dados.tipoOperacao()),
                    Categoria.fromString(dados.categoria()),
                    dados.valor(),
                    dados.origem()
            );

            caixaRepository.save(caixa);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Caixa inserido com sucesso!");

        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        }
    }

}
