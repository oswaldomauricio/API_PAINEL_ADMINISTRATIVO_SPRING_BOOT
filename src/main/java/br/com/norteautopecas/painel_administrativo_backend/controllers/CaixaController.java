package br.com.norteautopecas.painel_administrativo_backend.controllers;

import br.com.norteautopecas.painel_administrativo_backend.bussines.CaixaService;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.caixa.InserirCaixaDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/caixa")
@SecurityRequirement(name = "bearer-key")
public class CaixaController {

    @Autowired
    private CaixaService caixaService;

    @PostMapping("/inserir")
    @Transactional
    public ResponseEntity inserirValor(@RequestBody @Valid InserirCaixaDTO dados) {
        return caixaService.inserirCaixa(dados);
    }
}

