package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.roles.*;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Permissions;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Roles;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.PermissionsRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.RolesRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolesAndPermissions {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PermissionsRepository permissionsRepository;

    // =========================================================================
    // PERMISSIONS — CRUD
    // =========================================================================

    public ResponseEntity<?> criarPermission(CriarPermissionDTO dados) {
        if (permissionsRepository.existsByPermissionIgnoreCase(dados.permission())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe uma permissão com o nome: " + dados.permission());
        }

        Permissions perm = new Permissions(dados.permission());
        permissionsRepository.save(perm);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PermissionResponseDTO.fromEntity(perm));
    }

    public ResponseEntity<?> listarPermissions() {
        List<PermissionResponseDTO> lista = permissionsRepository.findAll()
                .stream()
                .map(PermissionResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    public ResponseEntity<?> buscarPermissionPorId(Long id) {
        Permissions perm = permissionsRepository.findById(id)
                .orElseThrow(() -> new ValidateException("Permissão não encontrada com ID: " + id));

        return ResponseEntity.ok(PermissionResponseDTO.fromEntity(perm));
    }

    public ResponseEntity<?> atualizarPermission(Long id, AtualizarPermissionDTO dados) {
        Permissions perm = permissionsRepository.findById(id)
                .orElseThrow(() -> new ValidateException("Permissão não encontrada com ID: " + id));

        // Verificar conflito de nome (ignorando o próprio registro)
        if (!perm.getPermission().equalsIgnoreCase(dados.permission())
                && permissionsRepository.existsByPermissionIgnoreCase(dados.permission())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe uma permissão com o nome: " + dados.permission());
        }

        perm.setPermission(dados.permission());
        perm.setUpdatedAt(LocalDateTime.now());
        permissionsRepository.save(perm);

        return ResponseEntity.ok(PermissionResponseDTO.fromEntity(perm));
    }

    public ResponseEntity<?> deletarPermission(Long id) {
        if (!permissionsRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Permissão não encontrada com ID: " + id);
        }

        permissionsRepository.deleteById(id);
        return ResponseEntity.ok("Permissão removida com sucesso!");
    }

    // =========================================================================
    // ROLES — CRUD
    // =========================================================================

    public ResponseEntity<?> criarRole(CriarRoleDTO dados) {
        if (rolesRepository.existsByNameIgnoreCase(dados.name())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe uma role com o nome: " + dados.name());
        }

        Roles role = new Roles();
        role.setName(dados.name());
        role.setCreatedAt(LocalDateTime.now());
        role.setPermissions(new ArrayList<>());
        rolesRepository.save(role);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RoleResponseDTO.fromEntity(role));
    }

    public ResponseEntity<?> listarRoles() {
        List<RoleResponseDTO> lista = rolesRepository.findAll()
                .stream()
                .map(RoleResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    public ResponseEntity<?> buscarRolePorId(Long id) {
        Roles role = rolesRepository.findById(id)
                .orElseThrow(() -> new ValidateException("Role não encontrada com ID: " + id));

        return ResponseEntity.ok(RoleResponseDTO.fromEntity(role));
    }

    public ResponseEntity<?> atualizarRole(Long id, AtualizarRoleDTO dados) {
        Roles role = rolesRepository.findById(id)
                .orElseThrow(() -> new ValidateException("Role não encontrada com ID: " + id));

        if (!role.getName().equalsIgnoreCase(dados.name())
                && rolesRepository.existsByNameIgnoreCase(dados.name())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe uma role com o nome: " + dados.name());
        }

        role.setName(dados.name());
        role.setUpdatedAt(LocalDateTime.now());
        rolesRepository.save(role);

        return ResponseEntity.ok(RoleResponseDTO.fromEntity(role));
    }

    public ResponseEntity<?> deletarRole(Long id) {
        if (!rolesRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Role não encontrada com ID: " + id);
        }

        rolesRepository.deleteById(id);
        return ResponseEntity.ok("Role removida com sucesso!");
    }

    // =========================================================================
    // ASSOCIAÇÃO: ROLE <-> PERMISSION
    // =========================================================================

    public ResponseEntity<?> adicionarPermissionNaRole(Long roleId, Long permissionId) {
        Roles role = rolesRepository.findById(roleId)
                .orElseThrow(() -> new ValidateException("Role não encontrada com ID: " + roleId));

        Permissions perm = permissionsRepository.findById(permissionId)
                .orElseThrow(() -> new ValidateException("Permissão não encontrada com ID: " + permissionId));

        if (role.getPermissions() == null) {
            role.setPermissions(new ArrayList<>());
        }

        boolean jaExiste = role.getPermissions().stream()
                .anyMatch(p -> p.getId().equals(permissionId));

        if (jaExiste) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("A permissão '" + perm.getPermission() + "' já está associada a esta role.");
        }

        role.getPermissions().add(perm);
        role.setUpdatedAt(LocalDateTime.now());
        rolesRepository.save(role);

        return ResponseEntity.ok(RoleResponseDTO.fromEntity(role));
    }

    public ResponseEntity<?> removerPermissionDaRole(Long roleId, Long permissionId) {
        Roles role = rolesRepository.findById(roleId)
                .orElseThrow(() -> new ValidateException("Role não encontrada com ID: " + roleId));

        if (role.getPermissions() == null || role.getPermissions().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Esta role não possui permissões associadas.");
        }

        boolean removido = role.getPermissions().removeIf(p -> p.getId().equals(permissionId));

        if (!removido) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("A permissão com ID " + permissionId + " não está associada a esta role.");
        }

        role.setUpdatedAt(LocalDateTime.now());
        rolesRepository.save(role);

        return ResponseEntity.ok(RoleResponseDTO.fromEntity(role));
    }
}
