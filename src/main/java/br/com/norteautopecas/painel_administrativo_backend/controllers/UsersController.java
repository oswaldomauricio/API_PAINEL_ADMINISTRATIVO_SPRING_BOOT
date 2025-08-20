package br.com.norteautopecas.painel_administrativo_backend.controllers;

import br.com.norteautopecas.painel_administrativo_backend.bussines.UsersService;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.users.AuthenticationDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.users.RegisterUserDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.users.UserRegistrationDataDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Roles;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.User;
import br.com.norteautopecas.painel_administrativo_backend.infra.security.TokenDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.security.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/v1")
public class UsersController {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dados) {
        var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var auth = manager.authenticate(authToken);
        var tokenJWT = tokenService.gerarToken((User) auth.getPrincipal());

        var usuario = (User) auth.getPrincipal();

        return ResponseEntity.ok(new TokenDTO(tokenJWT, usuario.getLogin(),
                usuario.getRole(), usuario.getId()));

    }

    @PostMapping("/register")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserRegistrationDataDTO> register(@RequestBody @Valid RegisterUserDTO dados, UriComponentsBuilder builder) {

        var user = usersService.registerUser(dados);

        var uri = builder.path("/register/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(new UserRegistrationDataDTO(user.getId(), user.getLogin(), user.getRole()));
    }

    @PutMapping("/user/alterarRegra/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserRegistrationDataDTO> alterarRegraDeUsuario(
            @PathVariable Long id,
            @RequestParam Roles role) {

        UserRegistrationDataDTO dto = usersService.alterarRegraDeUsuario(id, role);

        return ResponseEntity.ok(dto);
    }


}
