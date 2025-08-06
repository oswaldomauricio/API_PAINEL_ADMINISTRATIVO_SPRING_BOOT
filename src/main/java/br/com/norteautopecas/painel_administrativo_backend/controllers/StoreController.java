package br.com.norteautopecas.painel_administrativo_backend.controllers;

import br.com.norteautopecas.painel_administrativo_backend.bussines.StoreService;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.RegisterStoreDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.StoreRegistrationDetailsDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/v1/lojas")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @PostMapping
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<StoreRegistrationDetailsDTO> registerStore(@RequestBody @Valid RegisterStoreDTO dados, UriComponentsBuilder builder) {

        var store = storeService.registerStore(dados);

        return ResponseEntity.created(null).body(store);
    }
}
