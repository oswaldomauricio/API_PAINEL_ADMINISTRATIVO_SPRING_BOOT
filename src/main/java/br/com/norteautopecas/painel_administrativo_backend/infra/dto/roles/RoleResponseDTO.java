package br.com.norteautopecas.painel_administrativo_backend.infra.dto.roles;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Roles;

import java.util.List;
import java.util.stream.Collectors;

public record RoleResponseDTO(
        Long id,
        String name,
        List<PermissionResponseDTO> permissions
) {
    public static RoleResponseDTO fromEntity(Roles role) {
        List<PermissionResponseDTO> perms = role.getPermissions() == null
                ? List.of()
                : role.getPermissions().stream()
                        .map(PermissionResponseDTO::fromEntity)
                        .collect(Collectors.toList());

        return new RoleResponseDTO(role.getId(), role.getName(), perms);
    }
}
