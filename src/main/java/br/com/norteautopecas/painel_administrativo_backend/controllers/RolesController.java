package br.com.norteautopecas.painel_administrativo_backend.controllers;

import br.com.norteautopecas.painel_administrativo_backend.bussines.RolesAndPermissions;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.roles.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/acesso")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Roles e Permissões", description = "Gerenciamento de roles e permissões do sistema")
public class RolesController {

    @Autowired
    private RolesAndPermissions rolesAndPermissionsService;

    // =========================================================================
    // PERMISSIONS
    // =========================================================================

    @PostMapping("/permissions")
    @Transactional
    @Operation(summary = "Criar nova permissão")
    public ResponseEntity<?> criarPermission(@RequestBody @Valid CriarPermissionDTO dados) {
        return rolesAndPermissionsService.criarPermission(dados);
    }

    @GetMapping("/permissions")
    @Operation(summary = "Listar todas as permissões")
    public ResponseEntity<?> listarPermissions() {
        return rolesAndPermissionsService.listarPermissions();
    }

    @GetMapping("/permissions/{id}")
    @Operation(summary = "Buscar permissão por ID")
    public ResponseEntity<?> buscarPermission(@PathVariable Long id) {
        return rolesAndPermissionsService.buscarPermissionPorId(id);
    }

    @PutMapping("/permissions/{id}")
    @Transactional
    @Operation(summary = "Atualizar permissão")
    public ResponseEntity<?> atualizarPermission(@PathVariable Long id,
                                                  @RequestBody @Valid AtualizarPermissionDTO dados) {
        return rolesAndPermissionsService.atualizarPermission(id, dados);
    }

    @DeleteMapping("/permissions/{id}")
    @Transactional
    @Operation(summary = "Deletar permissão")
    public ResponseEntity<?> deletarPermission(@PathVariable Long id) {
        return rolesAndPermissionsService.deletarPermission(id);
    }

    // =========================================================================
    // ROLES
    // =========================================================================

    @PostMapping("/roles")
    @Transactional
    @Operation(summary = "Criar nova role")
    public ResponseEntity<?> criarRole(@RequestBody @Valid CriarRoleDTO dados) {
        return rolesAndPermissionsService.criarRole(dados);
    }

    @GetMapping("/roles")
    @Operation(summary = "Listar todas as roles com suas permissões",
               description = "Retorna cada role com a lista de permissões associadas.")
    public ResponseEntity<?> listarRoles() {
        return rolesAndPermissionsService.listarRoles();
    }

    @GetMapping("/roles/{id}")
    @Operation(summary = "Buscar role por ID com suas permissões")
    public ResponseEntity<?> buscarRole(@PathVariable Long id) {
        return rolesAndPermissionsService.buscarRolePorId(id);
    }

    @PutMapping("/roles/{id}")
    @Transactional
    @Operation(summary = "Atualizar nome da role")
    public ResponseEntity<?> atualizarRole(@PathVariable Long id,
                                            @RequestBody @Valid AtualizarRoleDTO dados) {
        return rolesAndPermissionsService.atualizarRole(id, dados);
    }

    @DeleteMapping("/roles/{id}")
    @Transactional
    @Operation(summary = "Deletar role")
    public ResponseEntity<?> deletarRole(@PathVariable Long id) {
        return rolesAndPermissionsService.deletarRole(id);
    }

    // =========================================================================
    // ASSOCIAÇÃO ROLE <-> PERMISSION
    // =========================================================================

    @PostMapping("/roles/{roleId}/permissions/{permissionId}")
    @Transactional
    @Operation(summary = "Adicionar permissão a uma role")
    public ResponseEntity<?> adicionarPermissaoNaRole(@PathVariable Long roleId,
                                                       @PathVariable Long permissionId) {
        return rolesAndPermissionsService.adicionarPermissionNaRole(roleId, permissionId);
    }

    @DeleteMapping("/roles/{roleId}/permissions/{permissionId}")
    @Transactional
    @Operation(summary = "Remover permissão de uma role")
    public ResponseEntity<?> removerPermissaoDaRole(@PathVariable Long roleId,
                                                     @PathVariable Long permissionId) {
        return rolesAndPermissionsService.removerPermissionDaRole(roleId, permissionId);
    }
}
