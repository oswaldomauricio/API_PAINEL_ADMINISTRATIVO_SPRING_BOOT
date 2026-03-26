package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa.CaixaResponseDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa.InserirCaixaDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa.ListarCaixaRequestDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa.ResumoCaixaResponseDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.User;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.caixa.Caixa;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.caixa.CategoriaCaixa;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.caixa.FundoCaixa;
import br.com.norteautopecas.painel_administrativo_backend.infra.enums.caixa.TipoOperacao;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.StoreInformationRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.StoreRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.caixa.CaixaRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.caixa.CategoriaCaixaRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.caixa.FundoCaixaRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CaixaService {

    /**
     * Permissão que permite registrar em data anterior ou posterior ao dia atual.
     */
    private static final String PERM_DATA_ANTERIOR_POSTERIOR = "INSERT:VALOR_DATA_ANTERIOR_POSTERIOR";

    /**
     * Permissão que permite visualizar valores por período (mais de um dia).
     */
    private static final String PERM_VISUALIZAR_POR_PERIODO = "VISUALIZAR:VALORES_POR_PERIODO";

    @Autowired
    private StoreInformationRepository storeInformationRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CaixaRepository caixaRepository;

    @Autowired
    private CategoriaCaixaRepository categoriaCaixaRepository;

    @Autowired
    private FundoCaixaRepository fundoCaixaRepository;

    // -------------------------------------------------------------------------
    // INSERÇÃO
    // -------------------------------------------------------------------------

    public ResponseEntity<?> inserirCaixa(InserirCaixaDTO dados) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User usuarioLogado = (User) auth.getPrincipal();

        // Regra: verificar se o usuário tem permissão na loja solicitada
        boolean temAcessoLoja = storeRepository.findAllByUserId(usuarioLogado.getId())
                .stream()
                .anyMatch(s -> s.getLoja().equals(dados.loja()));

        if (!temAcessoLoja) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Operação proibida: Você não tem acesso à loja " + dados.loja() + "!");
        }

        // Regra: verificar permissão para data anterior/posterior
        LocalDate hoje = LocalDate.now();
        if (!dados.dataInsercao().isEqual(hoje) && !temPermissao(auth, PERM_DATA_ANTERIOR_POSTERIOR)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Operação proibida: Você não tem permissão para registrar um valor em uma data anterior " +
                          "ou posterior à data atual! Peça liberação para o setor responsável.");
        }

        // Buscar loja
        var loja = storeInformationRepository.findByLoja(dados.loja())
                .orElseThrow(() -> new ValidateException("Loja não encontrada: " + dados.loja()));

        // Buscar categoria
        CategoriaCaixa categoria = categoriaCaixaRepository.findById(dados.idCategoria())
                .orElseThrow(() -> new ValidateException("Categoria não encontrada com ID: " + dados.idCategoria()));

        if (!categoria.getAtivo()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A categoria selecionada está inativa e não pode ser utilizada.");
        }

        // Converter tipo de operação
        TipoOperacao tipoOperacao;
        try {
            tipoOperacao = TipoOperacao.fromString(dados.tipoOperacao());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        Caixa caixa = new Caixa(
                loja,
                usuarioLogado,
                dados.dataInsercao(),
                dados.numeroDocumento(),
                tipoOperacao,
                categoria,
                dados.valor(),
                dados.origem()
        );

        caixaRepository.save(caixa);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Caixa inserido com sucesso!");
    }

    // -------------------------------------------------------------------------
    // LISTAGEM
    // -------------------------------------------------------------------------

    public ResponseEntity<?> listarCaixa(ListarCaixaRequestDTO dados) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User usuarioLogado = (User) auth.getPrincipal();

        // Regra: verificar se o usuário tem permissão na loja solicitada
        boolean temAcessoLoja = storeRepository.findAllByUserId(usuarioLogado.getId())
                .stream()
                .anyMatch(s -> s.getLoja().equals(dados.loja()));

        if (!temAcessoLoja) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Operação proibida: Você não tem acesso à loja " + dados.loja() + "!");
        }

        List<Caixa> registros;

        // Regra de visualização por período
        if (dados.dataFim() != null) {

            // Usuário quer buscar por período
            if (!temPermissao(auth, PERM_VISUALIZAR_POR_PERIODO)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Operação proibida: Você não tem permissão para visualizar valores por período. " +
                              "Apenas o dia selecionado pode ser visualizado.");
            }

            if (dados.dataFim().isBefore(dados.data())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A data final não pode ser anterior à data inicial.");
            }

            registros = caixaRepository.findByLojaAndPeriodo(dados.loja(), dados.data(), dados.dataFim());

        } else {
            // Busca apenas o dia selecionado — permitido para todos os usuários
            registros = caixaRepository.findByLojaAndData(dados.loja(), dados.data());
        }

        List<CaixaResponseDTO> response = registros.stream()
                .map(CaixaResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // -------------------------------------------------------------------------
    // RESUMO
    // -------------------------------------------------------------------------

    public ResponseEntity<?> resumoCaixa(ListarCaixaRequestDTO dados) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User usuarioLogado = (User) auth.getPrincipal();

        // Validar acesso à loja
        boolean temAcessoLoja = storeRepository.findAllByUserId(usuarioLogado.getId())
                .stream()
                .anyMatch(s -> s.getLoja().equals(dados.loja()));

        if (!temAcessoLoja) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Operação proibida: Você não tem acesso à loja " + dados.loja() + "!");
        }

        // Determinar o intervalo do período
        LocalDate dataInicio;
        LocalDate dataFim;

        if (dados.dataFim() != null) {
            // Usuário quer resumo por período
            if (!temPermissao(auth, PERM_VISUALIZAR_POR_PERIODO)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Operação proibida: Você não tem permissão para visualizar valores por período. " +
                              "Apenas o dia selecionado pode ser visualizado.");
            }
            if (dados.dataFim().isBefore(dados.data())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A data final não pode ser anterior à data inicial.");
            }
            dataInicio = dados.data();
            dataFim = dados.dataFim();
        } else {
            // Resumo de um único dia
            dataInicio = dados.data();
            dataFim = dados.data();
        }

        // Soma de entradas no período selecionado
        BigDecimal valorEntrada = caixaRepository.sumEntradasNoPeriodo(dados.loja(), dataInicio, dataFim);

        // Soma de saídas no período selecionado
        BigDecimal valorSaida = caixaRepository.sumSaidasNoPeriodo(dados.loja(), dataInicio, dataFim);

        // Saldo total acumulado desde sempre até a data final do período
        BigDecimal saldoTotal = caixaRepository.sumSaldoTotalAteData(dados.loja(), dataFim);

        // Fundo de caixa da loja (informativo — pode ser null se não cadastrado)
        BigDecimal fundoDeCaixa = fundoCaixaRepository.findByLojaLoja(dados.loja())
                .map(FundoCaixa::getValorFundo)
                .orElse(null);

        ResumoCaixaResponseDTO response = new ResumoCaixaResponseDTO(
                dados.loja(),
                dataInicio,
                dataFim,
                fundoDeCaixa,
                valorEntrada,
                valorSaida,
                saldoTotal
        );

        return ResponseEntity.ok(response);
    }

    // -------------------------------------------------------------------------
    // UTILITÁRIO
    // -------------------------------------------------------------------------

    private boolean temPermissao(Authentication auth, String permissao) {
        return auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equalsIgnoreCase(permissao));
    }
}
