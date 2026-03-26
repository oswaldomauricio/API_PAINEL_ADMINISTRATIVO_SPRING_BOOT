package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa.FundoCaixaRequestDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa.FundoCaixaResponseDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StoreInformation;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.caixa.FundoCaixa;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.StoreInformationRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.caixa.FundoCaixaRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FundoCaixaService {

    @Autowired
    private FundoCaixaRepository fundoCaixaRepository;

    @Autowired
    private StoreInformationRepository storeInformationRepository;

    /**
     * Cria ou atualiza o fundo de caixa de uma loja.
     * Se já existir um fundo para a loja, atualiza o valor.
     */
    public ResponseEntity<?> salvarFundoCaixa(FundoCaixaRequestDTO dados) {

        StoreInformation loja = storeInformationRepository.findByLoja(dados.loja())
                .orElseThrow(() -> new ValidateException("Loja não encontrada: " + dados.loja()));

        // Se já existe, atualiza o valor
        if (fundoCaixaRepository.existsByLojaLoja(dados.loja())) {
            FundoCaixa fundo = fundoCaixaRepository.findByLojaLoja(dados.loja())
                    .orElseThrow(() -> new ValidateException("Erro ao recuperar fundo de caixa da loja: " + dados.loja()));

            fundo.setValorFundo(dados.valorFundo());
            fundo.setUpdatedAt(LocalDateTime.now());
            fundoCaixaRepository.save(fundo);

            return ResponseEntity.ok(FundoCaixaResponseDTO.fromEntity(fundo));
        }

        // Cria novo registro
        FundoCaixa novoFundo = new FundoCaixa(loja, dados.valorFundo());
        fundoCaixaRepository.save(novoFundo);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(FundoCaixaResponseDTO.fromEntity(novoFundo));
    }

    /**
     * Consulta o fundo de caixa de uma loja.
     */
    public ResponseEntity<?> consultarFundoCaixa(Integer loja) {
        FundoCaixa fundo = fundoCaixaRepository.findByLojaLoja(loja)
                .orElseThrow(() -> new ValidateException(
                        "Fundo de caixa não cadastrado para a loja " + loja +
                        ". Cadastre o fundo de caixa antes de consultar."));

        return ResponseEntity.ok(FundoCaixaResponseDTO.fromEntity(fundo));
    }
}
