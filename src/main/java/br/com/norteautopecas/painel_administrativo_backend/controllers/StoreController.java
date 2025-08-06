package br.com.norteautopecas.painel_administrativo_backend.controllers;

import br.com.norteautopecas.painel_administrativo_backend.bussines.StoreService;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.ListStoreByUserDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.RegisterStoreDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.StoreRegistrationDetailsDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.user.StoreRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.ValidateException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/v1/lojas")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreRepository storeRepository;

    @PostMapping
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<StoreRegistrationDetailsDTO> registerStore(@RequestBody @Valid RegisterStoreDTO dados, UriComponentsBuilder builder) {

        var store = storeService.registerStore(dados);

        return ResponseEntity.created(null).body(store);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ListStoreByUserDTO>> listStoresByUser(@PathVariable Long id) {
        var stores = storeService.listStoresByUser(id);
        return ResponseEntity.ok().body(stores);
    }
}
