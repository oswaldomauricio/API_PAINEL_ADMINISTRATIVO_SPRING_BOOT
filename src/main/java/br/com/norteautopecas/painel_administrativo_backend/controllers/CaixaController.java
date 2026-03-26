package br.com.norteautopecas.painel_administrativo_backend.controllers;

import br.com.norteautopecas.painel_administrativo_backend.bussines.CaixaService;
import br.com.norteautopecas.painel_administrativo_backend.bussines.CategoriaCaixaService;
import br.com.norteautopecas.painel_administrativo_backend.bussines.FundoCaixaService;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa.CriarCategoriaCaixaDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa.FundoCaixaRequestDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa.InserirCaixaDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa.ListarCaixaRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/caixa")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Caixa", description = "Módulo de controle de caixa")
public class CaixaController {

    @Autowired
    private CaixaService caixaService;

    @Autowired
    private CategoriaCaixaService categoriaCaixaService;

    @Autowired
    private FundoCaixaService fundoCaixaService;

    // =========================================================================
    // LANÇAMENTOS
    // =========================================================================

    @PostMapping("/inserir")
    @Transactional
    @Operation(summary = "Inserir lançamento no caixa",
               description = "Requer permissão INSERT:VALOR_DATA_ANTERIOR_POSTERIOR para datas diferentes do dia atual.")
    public ResponseEntity<?> inserirValor(@RequestBody @Valid InserirCaixaDTO dados) {
        return caixaService.inserirCaixa(dados);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar lançamentos do caixa",
               description = "Busca por dia (apenas 'data') ou por período ('data' + 'data_fim'). " +
                             "Para período, é necessária a permissão VISUALIZAR:VALORES_POR_PERIODO.")
    public ResponseEntity<?> listarValores(@RequestBody @Valid ListarCaixaRequestDTO dados) {
        return caixaService.listarCaixa(dados);
    }

    // =========================================================================
    // RESUMO
    // =========================================================================

    @GetMapping("/resumo")
    @Operation(summary = "Resumo financeiro do caixa",
               description = "Retorna fundo_de_caixa, valor_entrada e valor_saida no período selecionado, " +
                             "e saldo_total acumulado histórico até a data informada. " +
                             "Para período (data + data_fim), requer VISUALIZAR:VALORES_POR_PERIODO.")
    public ResponseEntity<?> resumoCaixa(@RequestBody @Valid ListarCaixaRequestDTO dados) {
        return caixaService.resumoCaixa(dados);
    }

    // =========================================================================
    // FUNDO DE CAIXA
    // =========================================================================

    @PostMapping("/fundo")
    @Transactional
    @Operation(summary = "Cadastrar ou atualizar fundo de caixa de uma loja",
               description = "Se já existir um fundo cadastrado para a loja, o valor será atualizado.")
    public ResponseEntity<?> salvarFundoCaixa(@RequestBody @Valid FundoCaixaRequestDTO dados) {
        return fundoCaixaService.salvarFundoCaixa(dados);
    }

    @GetMapping("/fundo/{loja}")
    @Operation(summary = "Consultar fundo de caixa de uma loja")
    public ResponseEntity<?> consultarFundoCaixa(@PathVariable Integer loja) {
        return fundoCaixaService.consultarFundoCaixa(loja);
    }

    // =========================================================================
    // CATEGORIAS
    // =========================================================================

    @GetMapping("/categorias")
    @Operation(summary = "Listar categorias ativas")
    public ResponseEntity<?> listarCategorias() {
        return categoriaCaixaService.listarCategorias();
    }

    @GetMapping("/categorias/todas")
    @Operation(summary = "Listar todas as categorias (ativas e inativas)")
    public ResponseEntity<?> listarTodasCategorias() {
        return categoriaCaixaService.listarTodasCategorias();
    }

    @PostMapping("/categorias")
    @Transactional
    @Operation(summary = "Criar nova categoria de caixa")
    public ResponseEntity<?> criarCategoria(@RequestBody @Valid CriarCategoriaCaixaDTO dados) {
        return categoriaCaixaService.criarCategoria(dados);
    }

    @PatchMapping("/categorias/{id}/status")
    @Transactional
    @Operation(summary = "Ativar ou desativar uma categoria",
               description = "Passe o parâmetro 'ativo=true' para ativar ou 'ativo=false' para desativar.")
    public ResponseEntity<?> alterarStatusCategoria(@PathVariable Long id,
                                                     @RequestParam Boolean ativo) {
        return categoriaCaixaService.alterarStatusCategoria(id, ativo);
    }
}
