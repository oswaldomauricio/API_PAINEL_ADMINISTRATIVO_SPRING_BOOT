package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa.CategoriaCaixaResponseDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa.CriarCategoriaCaixaDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.caixa.CategoriaCaixa;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.caixa.CategoriaCaixaRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaCaixaService {

    @Autowired
    private CategoriaCaixaRepository categoriaCaixaRepository;

    /**
     * Lista todas as categorias ativas.
     */
    public ResponseEntity<List<CategoriaCaixaResponseDTO>> listarCategorias() {
        List<CategoriaCaixaResponseDTO> categorias = categoriaCaixaRepository.findAllByAtivoTrue()
                .stream()
                .map(CategoriaCaixaResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(categorias);
    }

    /**
     * Lista todas as categorias (ativas e inativas).
     */
    public ResponseEntity<List<CategoriaCaixaResponseDTO>> listarTodasCategorias() {
        List<CategoriaCaixaResponseDTO> categorias = categoriaCaixaRepository.findAll()
                .stream()
                .map(CategoriaCaixaResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(categorias);
    }

    /**
     * Cria uma nova categoria.
     */
    public ResponseEntity<?> criarCategoria(CriarCategoriaCaixaDTO dados) {
        if (categoriaCaixaRepository.existsByNomeIgnoreCase(dados.nome())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe uma categoria com o nome: " + dados.nome());
        }

        CategoriaCaixa categoria = new CategoriaCaixa(dados.nome());
        categoriaCaixaRepository.save(categoria);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoriaCaixaResponseDTO.fromEntity(categoria));
    }

    /**
     * Ativa/inativa uma categoria.
     */
    public ResponseEntity<?> alterarStatusCategoria(Long id, Boolean ativo) {
        CategoriaCaixa categoria = categoriaCaixaRepository.findById(id)
                .orElseThrow(() -> new ValidateException("Categoria não encontrada com ID: " + id));

        categoria.setAtivo(ativo);
        categoria.setUpdatedAt(java.time.LocalDateTime.now());
        categoriaCaixaRepository.save(categoria);

        String status = ativo ? "ativada" : "desativada";
        return ResponseEntity.ok("Categoria " + status + " com sucesso!");
    }
}
