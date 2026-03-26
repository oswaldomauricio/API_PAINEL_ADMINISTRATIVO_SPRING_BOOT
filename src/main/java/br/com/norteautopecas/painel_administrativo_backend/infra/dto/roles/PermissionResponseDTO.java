package br.com.norteautopecas.painel_administrativo_backend.infra.dto.roles;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Permissions;

public record PermissionResponseDTO(
        Long id,
        String permission
) {
    public static PermissionResponseDTO fromEntity(Permissions p) {
        return new PermissionResponseDTO(p.getId(), p.getPermission());
    }
}
